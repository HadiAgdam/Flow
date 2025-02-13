package ir.hadiagdamapps.flow

import ir.hadiagdamapps.flow.utils.toMinutes
import org.junit.Test

class TimeUnitTest {


    @Test
    fun longToMinutes() {

        assert((6000L).toMinutes() == "00:06")
        println((61000L).toMinutes() == "01:01")
        assert((123021L).toMinutes() == "02:03")

    }

}