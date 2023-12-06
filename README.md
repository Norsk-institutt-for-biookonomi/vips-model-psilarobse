# PsilaRosaeObservationModel

This repository contains the implementation of the Carrot rust fly (Psila rosae) observation model.

## Model description
<p>The warning system model is based on weekly observations of adult carrot rust flies captured on yellow sticky traps. The model is based in its entirety on observations, with no input of weather data or weather forecasts. Traps are placed in the field edge and in the field and are examined for carrot rust flies weekly throughout the season. The number of adult carrot rust flies is registered in VIPS and is used in the warning system model. The observations are compared with the economic threshold levels and a warning is calculated. After organophosphates (which had a good effect against larvae) were removed from the market, they were replaced by pyrethroids that only work against the adult stage. Studies were carried out in 2005 and 2006 to adjust the larval-based thresholds to chemical control of adult flies. The experience from Norway and other countries indicated that the first treatment against carrot rust flies should be done as soon as possible after the first fly is observed on the traps. The threshold that is used in VIPS is therefore at the first observation of 1 fly.</p>
<h3>Interpreting the warning</h3>
<p>Green rectangles indicate that the damage threshold has not been reached and there is no need for treatment.</p>
<p>Yellow rectangles indicate that carrot rust flies have been caught on traps at the edge of the field and it may be necessary to treat. The field should be checked to evaluate whether treatment is necessary.</p>
<p>Red rectangles indicate that carrot rust flies have been caught on traps in the field. The field should be checked to evaluate whether treatment is necessary.</p>
<p>The damage threshold for the first observation on sticky traps is 1 fly. Attack will vary from field to field, we recommend therefore that observations are made in addition in the farmer&rsquo;s own field. Control measures should be used as soon as possible after the first observation in the farmer&rsquo;s own field (if the plants are dry, without precipitation and the plants have reached the first true-leaf stage. Consider, based on previous experience and the size and shape of the field whether the entire field or only the border rows should be treated.</p>
<h3>The warning season - start and end of the&nbsp;forecast period</h3>
<p>Starting point: Registration of carrot rust flies on yellow sticky traps usually begins in May. The starting point is dependent on the temperature and sowing time in the region.</p>
<p>Ending: The registrations continue until the end of the growing season.</p>
<h3>Testing and validating the model</h3>
<h4>Nationally</h4>
<p>The damage thresholds that are the basis of the model are used in Norwegian carrot growing. After organophosphates (which had a good effect against larvae) were removed from the market, they were replaced by pyrethroids that only work against the adult stage. Studies were carried out in 2005 and 2006 to adjust the larval-based thresholds to chemical control of adult flies. The experience from Norway and other countries indicated that the first treatment against carrot rust flies should be done as soon as possible after the first fly is observed on the traps. The threshold that is used in VIPS is therefore at the first observation of 1 fly.</p>
<h4>Internationally</h4>
<p>There is a general understanding that with the chemical insecticides that are currently available, it is necessary to spray when the first carrot rust fly is found on the sticky traps. The damage threshold is usually 1 fly/trap/week or 0,2 flies/trap/day. In many countries day-degree models are used in addition to observations of flight period using yellow sticky traps. The threshold is normally 1 fly/trap/week or 0.2 flies/trap/day.</p>
<h3>References</h3>
<p>Contacts: Annette F. Schj&oslash;ll <a href="mailto:annette.folkedal.schjoll@nibio.no">annette.folkedal.schjoll@nibio.no</a><br /> Tor J. Johansen <a href="mailto:tor.johansen@nibio.no">tor.johansen@nibio.no</a></p>
<h3>Links for more information</h3>
<p>Plantevernleksikonet (Norwegian only):&nbsp;<a href="https://www.plantevernleksikonet.no/l/oppslag/62/" target="_blank">https://www.plantevernleksikonet.no/l/oppslag/62/</a></p>

### Development

Clone Gitlab repository

```
$ git clone https://gitlab.nibio.no/VIPS/models/java/Model_PSILAROBSE.git
$ cd Model_PSILAROBSE
```