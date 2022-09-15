package com.github.justadeni.minechem.enums

import org.bukkit.Note

enum class NoteEnum(val pitch: Float, val note: String) {
    NOTE_0(0.5f, "F#"),
    NOTE_1(0.53f, "G"),
    NOTE_2(0.56f, "G#"),
    NOTE_3(0.6f, "A"),
    NOTE_4(0.63f, "A#"),
    NOTE_5(0.67f, "B"),
    NOTE_6(0.7f, "C"),
    NOTE_7(0.76f, "C#"),
    NOTE_8(0.8f, "D"),
    NOTE_9(0.84f, "D#"),
    NOTE_10(0.9f, "E"),
    NOTE_11(0.94f, "F"),
    NOTE_12(1.0f, "F#"),
    NOTE_13(1.06f, "G"),
    NOTE_14(1.12f, "G#"),
    NOTE_15(1.18f, "A"),
    NOTE_16(1.26f, "A#"),
    NOTE_17(1.34f, "B"),
    NOTE_18(1.42f, "C"),
    NOTE_19(1.5f, "C#"),
    NOTE_20(1.6f, "D"),
    NOTE_21(1.68f, "D#"),
    NOTE_22(1.78f, "E"),
    NOTE_23(1.88f, "F"),
    NOTE_24(2.0f, "F#");

    companion object {

        fun getPitch(note : Int) : Float{
            for (value in values())
                if (value.ordinal == note)
                    return value.pitch

            return 0.0f
        }

        fun getNote(note : Int) : Note{

            val octave = if (note <= 11){
                0
            } else {
                1
            }

            lateinit var letter : String
            for (value in values())
                if (value.ordinal == note)
                    letter = value.note

            return if (letter.contains("#")){
                Note.sharp(octave, Note.Tone.valueOf(letter.replace("#","")))
            } else {
                Note.natural(octave, Note.Tone.valueOf(letter))
            }
        }
    }
}