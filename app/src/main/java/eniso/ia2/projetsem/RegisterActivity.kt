package eniso.ia2.projetsem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setupActionBar()
        setContentView(R.layout.activity_register)


        tv_login.setOnClickListener {
            //launch the register screen when the user clicks on the text.
            onBackPressed()
        }
        btn_register.setOnClickListener {
            registerUser()
        }
    }
       // private fun setupActionBar(){
          //  setSupportActionBar(toolbar_register_activity)
            //val actionBar = supportActionBar
           // if (actionBar!= null)
           // {
               // actionBar.setDisplayHomeAsUpEnabled(true)
               // actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
          //  }
           // toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }

        //}
    // **** a function to validate the entries of a new user ******
    private fun validateRegisterDetails():Boolean
    {
        return when
        {
            TextUtils.isEmpty(et_first_name.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name),true)
                false
            }
            TextUtils.isEmpty(et_last_name.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name),true)
                false
            }
            TextUtils.isEmpty(et_email1.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(et_password1.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }
            TextUtils.isEmpty(et_confirm_password.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password),true)
                false
            }
           et_password1.text.toString().trim{it <= ' '}!= et_confirm_password.text.toString().trim{it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),true)
                false
            }
            !cb_terms_and_conditions.isChecked ->
            {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition),true)
                false
            }
            else ->
            {
               // showErrorSnackBar("Your details are valid.",false)
                true
            }




        }
    }
    //function which will take care of the registration of a user
    private fun registerUser()
    {
        //check with validate function if the entires are valid or not.
        if (validateRegisterDetails()){

            showProgressDialog(resources.getString(R.string.please_wait))
            val email : String = et_email1.text.toString().trim {it <= ' '}
            val password : String = et_password1.text.toString().trim{it <= ' '}
            //create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> {task ->

                        hideProgressDialog()
                    // If the registration is successfully done
                    if(task.isSuccessful)
                    {
                        //Firebase registred user
                        val firebaseUser : FirebaseUser = task.result!!.user!!

                        showErrorSnackBar("you are registred successfuly. your user id is ${firebaseUser.uid}",
                        false)


                        FirebaseAuth.getInstance().signOut()
                        finish()
                    } else
                    {
                        // If the registering is not successful then show error message.
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }


                    }
                )
        }
    }


    }
