/*
 * Copyright (c) 2016 NIBIO <http://www.nibio.no/>. 
 * 
 * This file is part of PsilaRosaeObservationModel.
 * PsilaRosaeObservationModel is free software: you can redistribute it and/or modify
 * it under the terms of the NIBIO Open Source License as published by 
 * NIBIO, either version 1 of the License, or (at your option) any
 * later version.
 * 
 * PsilaRosaeObservationModel is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * NIBIO Open Source License for more details.
 * 
 * You should have received a copy of the NIBIO Open Source License
 * along with PsilaRosaeObservationModel.  If not, see <http://www.nibio.no/licenses/>.
 * 
 */
package no.nibio.vips.model.psilarosaeobservationmodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import no.nibio.vips.entity.ModelConfiguration;
import no.nibio.vips.entity.Result;
import no.nibio.vips.model.ModelId;
import no.nibio.vips.observation.Observation;
import no.nibio.vips.observation.ObservationImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author treinar
 */
public class PsilaRosaeObservationModelTest {
    
    public PsilaRosaeObservationModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getResult method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetResult() throws Exception {
        System.out.println("getResult");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        ModelConfiguration config = new ModelConfiguration();
        String tzID="Europe/Oslo";
        TimeZone timeZone = TimeZone.getTimeZone(tzID);
        config.setConfigParameter("timeZone", tzID);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(timeZone);

        config.setConfigParameter("startDateCalculation", "2016-04-01");
        config.setConfigParameter("endDateCalculation", "2016-06-01");
        
         List<Observation> pestObservations = new ArrayList<>();
        ObservationImpl obs1 = new ObservationImpl();
        obs1.setTimeOfObservation(format.parse("2016-04-20"));
        obs1.setObservationData("{\"trapCountCropEdge\":3,\"trapCountCropInside\":0}");
        pestObservations.add(obs1);
        
        ObservationImpl obs2 = new ObservationImpl();
        obs2.setTimeOfObservation(format.parse("2016-04-24"));
        obs2.setObservationData("{\"trapCountCropEdge\":1,\"trapCountCropInside\":5}");
        pestObservations.add(obs2);
        
        config.setConfigParameter("pestObservations", pestObservations);
        instance.setConfiguration(config);
        List<Result> result = instance.getResult();
        System.out.println("Number of results returned: " + result.size());
        for(Result res:result)
        {
            System.out.println(res);
        }
        assertNotNull(result);
    }

    /**
     * Test of getModelId method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetModelId() {
        System.out.println("getModelId");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String expResult = "PSILAROBSE";
        ModelId result = instance.getModelId();
        assertEquals(expResult, result.toString());
        
    }

    /**
     * Test of getModelName method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetModelName_0args() {
        System.out.println("getModelName");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String result = instance.getModelName();
        assertNotNull(result);
        
    }

   

    /**
     * Test of getLicense method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetLicense() {
        System.out.println("getLicense");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String result = instance.getLicense();
        assertNotNull(result);
    }

    /**
     * Test of getCopyright method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetCopyright() {
        System.out.println("getCopyright");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String result = instance.getCopyright();
        assertNotNull(result);
    }

    /**
     * Test of getModelDescription method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetModelDescription_0args() {
        System.out.println("getModelDescription");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String result = instance.getModelDescription();
        assertNotNull(result);
    }

    
    /**
     * Test of getWarningStatusInterpretation method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetWarningStatusInterpretation_0args() {
        System.out.println("getWarningStatusInterpretation");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String result = instance.getWarningStatusInterpretation();
        assertNotNull(result);
    }

    
    /**
     * Test of getModelUsage method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetModelUsage_0args() {
        System.out.println("getModelUsage");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String result = instance.getModelUsage();
        assertNotNull(result);
    }

    
    /**
     * Test of getSampleConfig method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetSampleConfig() {
        System.out.println("getSampleConfig");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        String result = instance.getSampleConfig();
        assertNotNull(result);
    }

    

    /**
     * Test of getObservationAtDate method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetObservationAtDate() throws Exception{
        System.out.println("getObservationAtDate");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        ModelConfiguration config = new ModelConfiguration();
        String tzID="Europe/Oslo";
        TimeZone timeZone = TimeZone.getTimeZone(tzID);
        config.setConfigParameter("timeZone", tzID);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(timeZone);

        config.setConfigParameter("startDateCalculation", "2016-04-01");
        config.setConfigParameter("endDateCalculation", "2016-06-01");
        
        List<Observation> pestObservations = new ArrayList<>();
        ObservationImpl obs1 = new ObservationImpl();
        obs1.setTimeOfObservation(format.parse("2016-04-20"));
        obs1.setObservationData("{\"trapCountCropEdge\":1,\"trapCountCropInside\":5}");
        pestObservations.add(obs1);
        /*ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(obs1));*/
        
        
        ObservationImpl obs2 = new ObservationImpl();
        obs2.setTimeOfObservation(format.parse("2016-04-24"));
        obs2.setObservationData("{\"trapCountCropEdge\":0,\"trapCountCropInside\":0}");
        pestObservations.add(obs2);
        
        config.setConfigParameter("pestObservations", pestObservations);
        
        instance.setConfiguration(config);
        
        Date testDate = format.parse("2016-04-21");
        Observation obs = instance.getObservationAtDate(testDate);
        assertNull(obs);
        
        testDate = format.parse("2016-04-25");
        obs = instance.getObservationAtDate(testDate);
        assertNull(obs);
        
        testDate = format.parse("2016-04-19");
        obs = instance.getObservationAtDate(testDate);
        assertNull(obs);
        
        testDate = format.parse("2016-04-20");
        obs = instance.getObservationAtDate(testDate);
        assertEquals(testDate,obs.getTimeOfObservation());
    }

    /**
     * Test of getPestObservationValues method, of class PsilaRosaeObservationModel.
     */
    @Test
    public void testGetPestObservationValues() {
        System.out.println("getPestObservationValues");
        String observationData = "{\"trapCountCropEdge\":1,\"trapCountCropInside\":5}";
        Integer expRes1 = 1;
        Integer expRes2 = 5;
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        
        Map<String, Integer> result = instance.getPestObservationValues(observationData);
        assertEquals(expRes1, result.get(PsilaRosaeObservationModel.TRAP_COUNT_CROP_EDGE));
        assertEquals(expRes2, result.get(PsilaRosaeObservationModel.TRAP_COUNT_CROP_INSIDE));
        
    }

    /**
     * Test of getObservationValidForDate method, of class PsilaRosaeObservationModel.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetObservationValidForDate() throws Exception{
        System.out.println("getObservationValidForDate");
        PsilaRosaeObservationModel instance = new PsilaRosaeObservationModel();
        ModelConfiguration config = new ModelConfiguration();
        String tzID="Europe/Oslo";
        TimeZone timeZone = TimeZone.getTimeZone(tzID);
        config.setConfigParameter("timeZone", tzID);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(timeZone);

        config.setConfigParameter("startDateCalculation", "2016-04-01");
        config.setConfigParameter("endDateCalculation", "2016-06-01");
        
        List<Observation> pestObservations = new ArrayList<>();
        ObservationImpl obs1 = new ObservationImpl();
        obs1.setTimeOfObservation(format.parse("2016-04-20"));
        obs1.setObservationData("{\"trapCountCropEdge\":0,\"trapCountCropInside\":0}");
        pestObservations.add(obs1);
        
        ObservationImpl obs2 = new ObservationImpl();
        obs2.setTimeOfObservation(format.parse("2016-04-24"));
        obs2.setObservationData("{\"trapCountCropEdge\":1,\"trapCountCropInside\":5}");
        pestObservations.add(obs2);
        
        config.setConfigParameter("pestObservations", pestObservations);
        
        instance.setConfiguration(config);
        
        Date testDate = format.parse("2016-04-21");
        Observation obs = instance.getObservationValidForDate(testDate);
        assertEquals(format.parse("2016-04-20"), obs.getTimeOfObservation());
        
        testDate = format.parse("2016-04-25");
        obs = instance.getObservationValidForDate(testDate);
        assertEquals(format.parse("2016-04-24"), obs.getTimeOfObservation());
        
        testDate = format.parse("2016-04-19");
        obs = instance.getObservationValidForDate(testDate);
        assertNull(obs);
    }
    
}
