package hue.com.workbench;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;import java.lang.Override;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private CallbackManager callbackManager;
    public static Profile profile;
    private ProfilePictureView profileImage;
    FacebookCallback<LoginResult> mcallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            profile = Profile.getCurrentProfile();
            Toast.makeText(getActivity(), profile.getFirstName() + " " +profile.getLastName(), Toast.LENGTH_SHORT).show();
//            profileImage.setProfileId(profile.getId());
            startActivity(new Intent(getActivity(), MaterialMain.class));
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException exception) {

        }

    };
    private ImageView profPic;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        profile = Profile.getCurrentProfile();
        Toast.makeText(getActivity(), profile.getFirstName() + " " +profile.getLastName(), Toast.LENGTH_SHORT).show();
        if (!MaterialMain.logout_button_clicked) {
            startActivity(new Intent(getActivity(), MaterialMain.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button b_debug = (Button)view.findViewById(R.id.b_debug);
        super.onViewCreated(view, savedInstanceState);


        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, mcallBack);
        profileImage = (ProfilePictureView) view.findViewById(R.id.profilePicture);
        profileImage.setPresetSize(ProfilePictureView.SMALL);
        profileImage.setCropped(false);


    }

    public void DebugButton(View V) {
        startActivity(new Intent(getActivity(), MaterialMain.class));
    }
}
