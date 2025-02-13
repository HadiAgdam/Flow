package ir.hadiagdamapps.flow.utils


fun Long.toMinutes(): String {

    val minute = (this / 60000).toInt().toString()
    val second = ((this / 1000) % 60).toString()

    return "${if(minute.length == 1) "0" else ""}$minute:${if(second.length == 1) "0" else ""}$second"
}
