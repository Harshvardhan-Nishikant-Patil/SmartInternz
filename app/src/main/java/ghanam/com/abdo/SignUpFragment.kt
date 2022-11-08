package ghanam.com.abdo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import ghanam.com.abdo.databinding.FragmentSignUpBinding


class  SignUpFragment : Fragment() {

    lateinit var binding:FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        firebaseAuth=FirebaseAuth.getInstance()
        binding.signUpButton.setOnClickListener {
            validateSignUp(it)
        }

        binding.loginLink.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signUpFragment_to_loginFragment)
        }


        return binding.root
    }

    private fun validateSignUp(it: View?) {

        binding.apply {
            val email= emailTextInput.text.toString().trim()
            val password=passwordTextInput.text.toString().trim()
            val confirmPassword= confirmPasswordTextInput.text.toString()
            if (email.isEmpty() or password.isEmpty() or confirmPassword.isEmpty()){
                Toast.makeText(context,"Please complete all fields!",Toast.LENGTH_LONG).show()
                return
            }
            if (password == confirmPassword){
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context,"User created successfully", android.widget.Toast.LENGTH_SHORT).show()
                        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_signUpFragment_to_loginFragment) }
                    }else{
                        Toast.makeText(context,it.exception.toString(), android.widget.Toast.LENGTH_SHORT).show()
                    }
                }
                return
            }else if(password == confirmPassword){
                android.widget.Toast.makeText(context,"password not matching", android.widget.Toast.LENGTH_LONG).show()
                return
            }
//            if (it != null) {
//                Navigation.findNavController(it).navigate(R.id.action_signUpFragment_to_loginFragment)
//            }

        }

    }


}