/*
 * Copyright (c) 2016 NIBIO <http://www.nibio.no/>. 
 * 
 * This file is part of PsilaRosaeObservationModel.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * 
 */

package no.nibio.vips.model.psilarosaeobservationmodel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import no.nibio.vips.entity.ModelConfiguration;
import no.nibio.vips.entity.Result;
import no.nibio.vips.entity.ResultImpl;
import no.nibio.vips.i18n.I18nImpl;
import no.nibio.vips.model.ConfigValidationException;
import no.nibio.vips.model.Model;
import no.nibio.vips.model.ModelExcecutionException;
import no.nibio.vips.model.ModelId;
import no.nibio.vips.observation.Observation;
import no.nibio.vips.observation.ObservationImpl;
import no.nibio.vips.util.ModelUtil;
import no.nibio.vips.util.WeatherUtil;

/**
 * @copyright 2016 <a href="http://www.nibio.no/">NIBIO</a>
 * @author Tor-Einar Skog <tor-einar.skog@nibio.no>
 */
public class PsilaRosaeObservationModel  extends I18nImpl implements Model{

    public final static ModelId MODEL_ID = new ModelId("PSILAROBSE");
    
    public final static String TRAP_COUNT_CROP_EDGE = "trapCountCropEdge";
    public final static String TRAP_COUNT_CROP_INSIDE = "trapCountCropInside";
    
    private final Integer observationMaximumValidDays = 9;
    
    private ObjectMapper objectMapper;
    private final ModelUtil modelUtil;
    private final WeatherUtil weatherUtil;
    
    private List<Observation> pestObservations;
    private TimeZone timeZone;
    private Date startDateCalculation;
    private Date endDateCalculation;
    
    public PsilaRosaeObservationModel()
    {
        super("no.nibio.vips.model.psilarosaeobservationmodel.texts");
        this.modelUtil = new ModelUtil();
        this.weatherUtil = new WeatherUtil();
    }
    
    @Override
    public List<Result> getResult() throws ModelExcecutionException {
        List<Result> retVal = new ArrayList<>();
        Calendar cal = Calendar.getInstance(timeZone);
        
        Date currentDate = this.startDateCalculation;
        while(currentDate.compareTo(endDateCalculation) <= 0)
        {
            Result result = new ResultImpl();
            result.setValidTimeStart(currentDate);
            
            // Setting result data 
            Observation todayObs = getObservationAtDate(currentDate);
            if(todayObs != null)
            {
                Map<String, Integer> data = this.getPestObservationValues(todayObs.getObservationData());
                result.setValue(this.getModelId().toString(), "TRAP_COUNT_CROP_EDGE", 
                        String.valueOf(data.get(PsilaRosaeObservationModel.TRAP_COUNT_CROP_EDGE))
                );
                result.setValue(this.getModelId().toString(), "TRAP_COUNT_CROP_INSIDE", 
                        String.valueOf(data.get(PsilaRosaeObservationModel.TRAP_COUNT_CROP_INSIDE))
                );
            }
            
            result.setValue(this.getModelId().toString(), "TRAP_COUNT_THRESHOLD","1");
            
            // Deciding warning status
            Observation latestObs = getObservationValidForDate(currentDate);
            if(latestObs == null)
            {
                result.setWarningStatus(Result.WARNING_STATUS_NO_WARNING_MISSING_DATA);
            }
            else
            {
                result.setWarningStatus(Result.WARNING_STATUS_NO_RISK);
                Map<String, Integer> data = this.getPestObservationValues(latestObs.getObservationData());
                Integer trapCountCropEdge = data.get(PsilaRosaeObservationModel.TRAP_COUNT_CROP_EDGE);
                Integer trapCountCropInside = data.get(PsilaRosaeObservationModel.TRAP_COUNT_CROP_INSIDE);

                if(trapCountCropEdge > 0)
                {
                    result.setWarningStatus(Result.WARNING_STATUS_MINOR_RISK);
                }
                if(trapCountCropInside > 0)
                {
                    result.setWarningStatus(Result.WARNING_STATUS_HIGH_RISK);
                }
            }
            retVal.add(result);
            
             // Move forward in time    
            cal.setTime(currentDate);
            cal.add(Calendar.DATE, 1);
            currentDate = cal.getTime();
        }
        return retVal;
    }

    @Override
    public ModelId getModelId() {
        return PsilaRosaeObservationModel.MODEL_ID;
    }

    @Override
    public String getModelName() {
        return this.getModelName(Model.DEFAULT_LANGUAGE);
    }

    @Override
    public String getModelName(String language) {
        return this.getText("name", language);
    }

    @Override
    public String getLicense() {
        return "Copyright (c) 2016 NIBIO <http://www.nibio.no/>. \n" +
            "\n" +
            "This file is part of PsilaRosaeObservationModel. \n" + 
            "PsilaRosaeObservationModel is free software: you can redistribute it and/or modify \n" + 
            "it under the terms of the NIBIO Open Source License as published by \n" + 
            "NIBIO, either version 1 of the License, or (at your option) any \n" +
            "later version. \n" +
            "\n" +
            "PsilaRosaeObservationModel is distributed in the hope that it will be useful, \n" +
            "but WITHOUT ANY WARRANTY; without even the implied warranty of \n" +
            "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the \n" +
            "NIBIO Open Source License for more details. \n" +
            "" +
            "You should have received a copy of the NIBIO Open Source License \n" +
            "along with PsilaRosaeObservationModel.  If not, see <http://www.nibio.no/licenses/>. \n";
    }

    @Override
    public String getCopyright() {
        return "(c) 2016 NIBIO (http://www.nibio.no/). Contact: post@nibio.no";
    }

    @Override
    public String getModelDescription() {
        return this.getModelDescription(Model.DEFAULT_LANGUAGE);
    }

    @Override
    public String getModelDescription(String language) {
        try
        {
            return this.modelUtil.getTextWithBase64EncodedImages(this.getText("description", language), this.getClass());
        }
        catch(IOException ex)
        {
            return this.getText("description", language);
        }
    }

    @Override
    public String getWarningStatusInterpretation() {
        return this.getWarningStatusInterpretation(Model.DEFAULT_LANGUAGE);
    }

    @Override
    public String getWarningStatusInterpretation(String language) {
        return this.getText("statusInterpretation", language);
    }

    @Override
    public String getModelUsage() {
        return this.getModelUsage(Model.DEFAULT_LANGUAGE);
    }

    @Override
    public String getModelUsage(String language) {
        return this.getText("usage", language);
    }

    @Override
    public String getSampleConfig() {
        return  "{\n" +
                "\t\"loginInfo\":{\n" +
                "\t\t\"username\":\"example\",\n" +
                "\t\t\"password\":\"example\"\n" +
                "\t},\n" +
                "\t\"modelId\":\"" + MODEL_ID.toString() + "\",\n" +
                "\t\"configParameters\":{\n" +
                "\t\t\"timeZone\":\"Europe/Oslo\",\n" +
                "\t\t\"startDateCalculation\":\"2012-03-25\",\n" +
                "\t\t\"endDateCalculation\":\"2012-03-25\",\n" +
                "\t\t\"pestObservations\":[\n" +
                "\t\t\t{" +
                "\t\t\t\t\"timeOfObservation\":\"2016-04-20T00:00:00+02:00\"," +
                "\t\t\t\t\"observationData\":{" +
                "\t\t\t\t\t\"trapCountCropEdge\":3," +
                "\t\t\t\t\t\"trapCountCropInside\":2" +
                "\t\t\t\t}" +
                "\t\t\t\"}," +
                "\t\t\t{" +
                "\t\t\t\t\"timeOfObservation\":\"2016-04-25T00:00:00+02:00\"," +
                "\t\t\t\t\"observationData\":{" +
                "\t\t\t\t\t\"trapCountCropEdge\":0," +
                "\t\t\t\t\t\"trapCountCropInside\":0" +
                "\t\t\t\t}" +
                "\t\t\t\"}" +
                "\t\t]\n" +
                "\t}\n" +
                "}\n";
    }

    @Override
    public void setConfiguration(ModelConfiguration config) throws ConfigValidationException {
        this.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.pestObservations = this.getObjectMapper().convertValue(config.getConfigParameter("pestObservations"), new TypeReference<List<Observation>>(){});
        Collections.sort(this.pestObservations);
        // Setting timezone
        this.timeZone = TimeZone.getTimeZone((String) config.getConfigParameter("timeZone"));
        String[] dateparts;
        String dateParameter = "";
        try
        {
            dateParameter = "startDateCalculation";
            dateparts = config.getConfigParameter(dateParameter).toString().split("-");
            dateParameter = "endDateCalculation";
            dateparts = config.getConfigParameter(dateParameter).toString().split("-");
        }
        catch (NullPointerException ex){
            throw new ConfigValidationException(dateParameter + " not set");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(timeZone);
        try
        {
          
            dateParameter = "startDateCalculation";
            this.startDateCalculation = format.parse(config.getConfigParameter("startDateCalculation").toString());
            dateParameter = "endDateCalculation";
            this.endDateCalculation = format.parse(config.getConfigParameter("endDateCalculation").toString());
        }
        catch(ParseException | NullPointerException ex)
        {
            throw new ConfigValidationException("Illegal date format for " + dateParameter + ": " + config.getConfigParameter(dateParameter));
        }
    }

    private ObjectMapper getObjectMapper()
    {
        if(this.objectMapper == null)
        {
            this.objectMapper = new ObjectMapper();
        }
        return this.objectMapper;
    }
    
    public Observation getObservationAtDate(Date currentDate) {
        if(this.pestObservations == null || this.pestObservations.isEmpty())
        {
            return null;
        }
        for(Observation obs:this.pestObservations)
        {
            if(this.weatherUtil.normalizeToExactDate(obs.getTimeOfObservation(), timeZone).compareTo(currentDate) == 0)
            {
                return obs;
            }
        }
        return null;
    }
    
    /**
     * Parses the JSON and returns the average number
     * @param observationData
     * @return 
     */
    public Map<String, Integer> getPestObservationValues(String observationData)
    {
        try
        {
            Map<String, Integer> data = this.getObjectMapper().readValue(new ByteArrayInputStream(observationData.getBytes("UTF-8")), new TypeReference<HashMap<String,Integer>>() {});
            return data;
        }
        catch(IOException ex)
        {
            return null;
        }
    }
    
    public Observation getObservationValidForDate(Date currentDate) {
        if(this.pestObservations == null || this.pestObservations.isEmpty())
        {
            return null;
        }
        Long currentTime = currentDate.getTime();
        Observation currentObservation = null;
        for(Observation obs:this.pestObservations)
        {
            Long obsTime = this.weatherUtil.normalizeToExactDate(obs.getTimeOfObservation(), timeZone).getTime();
            if( currentTime - obsTime >= 0 // Must be before the current date
                && currentTime - obsTime < this.observationMaximumValidDays * (24 * 3600 * 1000) // Must be maximum [9] days before current date
                && (currentObservation == null || currentTime - obsTime < currentTime - currentObservation.getTimeOfObservation().getTime()) // Must be closer to current date than any other observations
            )
            {
                currentObservation = obs;
            }
        }
        return currentObservation;
    }
}
