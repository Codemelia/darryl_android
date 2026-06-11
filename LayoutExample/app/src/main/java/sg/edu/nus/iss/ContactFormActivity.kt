package sg.edu.nus.iss

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.Calendar

class ContactFormActivity : AppCompatActivity() {

    private lateinit var editFullName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editDob: EditText
    private lateinit var editPin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        editFullName = findViewById(R.id.editFullName)
        editEmail = findViewById(R.id.editEmail)
        editDob = findViewById(R.id.editDob)
        editPin = findViewById(R.id.editPin)

        editDob.setOnClickListener { showDatePicker() }

        findViewById<Button>(R.id.buttonPreview).setOnClickListener { showPreview() }
        findViewById<Button>(R.id.buttonSave).setOnClickListener { saveContact() }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                editDob.setText("%04d-%02d-%02d".format(year, month + 1, day))
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun validate(): Boolean {
        val name = editFullName.text.toString().trim()
        val email = editEmail.text.toString().trim()
        val dob = editDob.text.toString().trim()
        val pin = editPin.text.toString()

        if (name.isEmpty()) {
            editFullName.error = "Full name is required"
            editFullName.requestFocus()
            return false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.error = "Enter a valid email address"
            editEmail.requestFocus()
            return false
        }
        if (dob.isEmpty()) {
            editDob.error = "Please select a date of birth"
            return false
        }
        if (pin.length != 6) {
            editPin.error = "PIN must be exactly 6 digits"
            editPin.requestFocus()
            return false
        }
        return true
    }

    private fun showPreview() {
        if (!validate()) return

        val message = """
            Full Name   :  ${editFullName.text.toString().trim()}
            Email       :  ${editEmail.text.toString().trim()}
            Date of Birth:  ${editDob.text.toString().trim()}
            PIN Code    :  ${editPin.text.toString()}
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Contact Preview")
            .setMessage(message)
            .setPositiveButton("Close", null)
            .show()
    }

    private fun saveContact() {
        if (!validate()) return

        val contact = JSONObject().apply {
            put("fullName", editFullName.text.toString().trim())
            put("email", editEmail.text.toString().trim())
            put("dateOfBirth", editDob.text.toString().trim())
            put("pinCode", editPin.text.toString())
        }

        val file = File(filesDir, "contact.json")
        val contacts = if (file.exists()) JSONArray(file.readText()) else JSONArray()
        contacts.put(contact)
        file.writeText(contacts.toString(2))

        Toast.makeText(this, "Contact details have been saved.", Toast.LENGTH_SHORT).show()
        clearForm()
    }

    private fun clearForm() {
        editFullName.text.clear()
        editEmail.text.clear()
        editDob.text.clear()
        editPin.text.clear()
        editFullName.requestFocus()
    }
}
