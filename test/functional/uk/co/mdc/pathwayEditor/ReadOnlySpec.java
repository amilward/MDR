package uk.co.mdc.pathwayEditor;

import geb.spock.GebReportingSpec;

/**
 * Created by rb on 17/01/2014.
 */
public class ReadOnlySpec extends GebReportingSpec{

    //Given: I am a user with permission to view but not edit the pathway

    //When: the pathway is displayed
    //Then: the elements are selectable and navigable but can't be moved or edited (presentation mode)

    //Given: I am a user with permission to edit the pathway

    //When: I click on the "presentation mode" button
    //Then: the same read-only display is presented as for a read-only user
}
