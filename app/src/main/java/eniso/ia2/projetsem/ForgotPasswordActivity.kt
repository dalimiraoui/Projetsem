package eniso.ia2.projetsem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
  toolbar_forgot_password_activity.setNavigationOnClickListener { onBackPressed() }
        btn_submit.setOnClickListener{
            val email :String = et_email_forgot_pw.text.toString().trim{it <= ' '}
            if(email.isEmpty())
            {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
            }else {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        hideProgressDialog()
                        if (task.isSuccessful){
                            // show the toast message and finish the forgot
                        // password activity to go back to the login
                            Toast.makeText(this@ForgotPasswordActivity,resources.getString(R.string.email_sent_success),
                            Toast.LENGTH_LONG).show()
                            finish()
                        }else {
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
            }
        }
    }
}