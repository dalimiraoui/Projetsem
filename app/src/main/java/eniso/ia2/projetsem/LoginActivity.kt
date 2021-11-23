package eniso.ia2.projetsem

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN

            )
        }
        // remove title
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(
            //WindowManager.LayoutParams.FLAG_FULLSCREEN,
            //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //click event assigned to forgot password text.
        tv_forgot_password.setOnClickListener(this)
        //click event assigned to Login Button.
        btn_login.setOnClickListener(this)
        //click event assigned to register text.
        tv_register.setOnClickListener(this)
    }

    //In Login screen the clickable components are login Button , ForgotPassword text and register text.
    override fun onClick(view: View?) {
        if (view!= null)
        {
            when(view.id)
            {
                R.id.tv_forgot_password ->
                {
                    val intent = Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_login -> {
                    logInRegisteredUser()



                }
                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                    startActivity(intent)
                }


            }
        }
    }


    private fun validateLoginDetails():Boolean {
        return when {


            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_email),
                    true
                )
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_password),
                    true)
                false
            }
            else -> {
                 //showErrorSnackBar("Your details are valid.",false)
                true
            }
        }
    }

    private fun logInRegisteredUser()
    {
        if (validateLoginDetails())
        {
            //show the process dialog.
            showProgressDialog((resources.getString(R.string.please_wait)))
            //Get the text from edittext and trim the space
            val email = et_email.text.toString().trim{it <= ' '}
            val password = et_password.text.toString().trim{it <= ' '}
            // now log in user FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    //hide the progress dialog
                    hideProgressDialog()
                    if(task.isSuccessful)
                    {
                        //TODO - send user to Main Activity !!
                        showErrorSnackBar("you are logged in successfully.",false)
                    }else {
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }


        }
    }

}