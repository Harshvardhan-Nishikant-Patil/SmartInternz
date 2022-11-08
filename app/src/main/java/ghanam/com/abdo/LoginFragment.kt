package ghanam.com.abdo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import ghanam.com.abdo.databinding.FragmentLoginBinding
import ghanam.com.abdo.singletons.VirtualDB


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        VirtualDB.toString()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth=FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            validateLogin(it)
        }
        binding.singUpLink.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        return binding.root
    }



    private fun validateLogin(it: View?){
        binding.apply {
            val email = emailTextInput.text.toString().trim()
            val password = passwordTextInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context,"Welcome", android.widget.Toast.LENGTH_SHORT).show()
                        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_loginFragment_to_homeFragment) }

                    }else{
                        Toast.makeText(context,it.exception.toString(), android.widget.Toast.LENGTH_SHORT).show()
                    }

                }
                return
            }else{
                Toast.makeText(context,"please complete all fields",Toast.LENGTH_LONG).show()
            }
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {

                if (it != null) {
                    Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_homeFragment)
                }
            } else {
                // User is signed out
                Toast.makeText(context, "please sign in", Toast.LENGTH_SHORT).show()
            }

        }
    }



}