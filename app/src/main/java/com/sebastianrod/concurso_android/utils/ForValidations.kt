package com.sebastianrod.concurso_android.utils

import android.view.View
import android.widget.EditText
import android.widget.TextView

class ForValidations {
    companion object {

        private var customMessage = ""

        fun removeBlanks(text:String):String{
            return text.replace("\\s{2,}".toRegex(), " ") //Eliminando los intermedios
                .replace("(^\\s|\\s$)".toRegex(), "") //Eliminando los iniciales y finales
        }

        fun valOnlyText(text:String):Boolean{
            val regOnlyString = Regex("^[a-zA-Záéíóú ñ]+$")
            customMessage = "Este campo solo acepta letras"
            return regOnlyString.containsMatchIn(text)
        }

        //Valida cualquier texto que no tenga saltos de línea.
        fun valAllTypeText(text:String):Boolean{
            val regTextAndNumbers = Regex("^.+$")
            return regTextAndNumbers.containsMatchIn(text)
        }

        //Valida números con una cantidad de dígitos específica si así se quiere
        fun valNumber(text:String, numberOfDigits:Int = 0):Boolean{
            val regOnlyNumber:Regex = if (numberOfDigits == 0) Regex("^[0-9]+$") else Regex("^[0-9]{$numberOfDigits}$")
            customMessage = "Este campo solo acepta números"
            return regOnlyNumber.containsMatchIn(text)
        }

        fun valDecimal(text:String):Boolean{
            val regOnlyDecimal:Regex = Regex("^[0-9]+(\\.[0-9]+|\\.)?\$")
            customMessage = "Este campo solo acepta decimales"
            return regOnlyDecimal.containsMatchIn(text)
        }

        fun valEmptyText(text: String):Boolean{
            val regEmptyText = Regex("^\\s+\$")
            return regEmptyText.containsMatchIn(text) || text == ""
        }

        fun valInputWithoutErrorMessage(input:EditText, valFunction: (text:String) -> Boolean):Boolean{
            val text = input.text.toString()
            return valFunction(text)
        }


        fun valInput(input:EditText, errorMessage:TextView, valFunction: (text:String) -> Boolean): Boolean {
            val text = removeBlanks(input.text.toString())
            var existError = true
            var finalMessage = ""

            if (valEmptyText(text)){
                finalMessage = "Debes completar el campo"
            } else if (!valFunction(text)){
                finalMessage = customMessage
            } else {
                existError = false
            }
            println(valFunction)
            println(existError)

            errorMessage.text = finalMessage
            errorMessage.visibility = if (existError) View.VISIBLE else View.INVISIBLE
            return existError
        }



    }
}