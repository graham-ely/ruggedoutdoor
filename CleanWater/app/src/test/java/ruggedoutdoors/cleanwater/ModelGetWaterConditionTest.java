package ruggedoutdoors.cleanwater;

import org.junit.Before;
import org.junit.Test;

import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.OverallCondition;
import ruggedoutdoors.cleanwater.model.PurityReport;
import ruggedoutdoors.cleanwater.model.SourceReport;
import ruggedoutdoors.cleanwater.model.WaterCondition;
import ruggedoutdoors.cleanwater.model.WaterType;

import static junit.framework.Assert.assertEquals;

/**
 * Created by gde on 4/10/17.
 */

/*
 * Tests whether the model properly handles get the water condition for each of the report types
 */
public class ModelGetWaterConditionTest {
    /*
     * Model with which to test
     */
    private Model model;
    private PurityReport testPurity;
    private SourceReport testSource;

    /*
     * Creates fake reports to test on before the tests
     */
    @Before
    public void create() {
        //instantiate model
        model = Model.getInstance();

        //adds two reports to the model
        model.addPurityReport( 10, 10, OverallCondition.SAFE.toString(), 10, 10 );
        model.addSourceReport( 20, 20, WaterType.BOTTLED.toString(), WaterCondition.POTABLE.toString() );

        //grabs the reports
        testPurity = model.getPurityReportArray().get(0);
        testSource = model.getSourceReportArray().get(0);
    }

    @Test
    /*
     * Asserts that the water condition from the model matches that of the report
     */
    public void testWaterConditionSourceReport() {
        model.setActiveSourceReport( testSource.getReportNumber() );
        assertEquals( model.getWaterCondition().toString(),  testSource.getWaterCondition().toString() );
    }

    @Test
    /*
     * Asserts that the water condition from the model matches the overall condition of the report
     */
    public void testWaterConditionPurityReport() {
        model.setActivePurityReport( testPurity.getReportNumber() );
        assertEquals( model.getWaterCondition().toString(), testPurity.getOverallCondition().toString() );
    }

}
