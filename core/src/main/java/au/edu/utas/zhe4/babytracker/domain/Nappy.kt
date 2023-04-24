package au.edu.utas.zhe4.babytracker.domain

import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLong

enum class NappyCons {
    WET, WET_DIRTY
}

class Nappy() {
    var id : String? = null
    var time : Long? = System.currentTimeMillis()
    var cons : NappyCons? = NappyCons.WET
    var image: String? = ""
    var imageName: String? = ""
    var note : String? = ""
}

fun createNappy(
    id : String? = null,
    time : String? = LongToLocalDateTimeString(CurrentTime()),
    cons : String? = NappyCons.WET.toString(),
    image: String? = "",
    imageName: String? = "",
    note : String? = "",
) : Nappy
{
    val nappy = Nappy()

    nappy.id = id
    nappy.time = TimeStringToLong(time!!)
    nappy.cons = if (cons==NappyCons.WET.toString()) NappyCons.WET else NappyCons.WET_DIRTY
    nappy.image = image
    nappy.imageName = imageName
    nappy.note = note

    return nappy
}