# Functionality
The app's functionality includes:
1. Register with user's data & save into local database
2. Login with username, password
3. After successful login takes the user to the home page.
4. Login session expires if a user is in the background for more than 10 seconds or foreground for more than 30 seconds.
5. The app uses a custom button component, which can be used to configure different custom properties like image, sub-title, color, ripple, shape, border, corner radius, etc.
6. User can log out from the session.

### RegisterFragment
Initial screen - when the user first opens the app. It shows text fields (full name, username, password) for users to fill up and register in the app.
There are rules set up for username & password.
a) Username must be greater than 5 characters.
b) Password must be greater than 3 characters.
c) Password must contain 1 digit.
d) Password must contain 1 lowercase character.
e) Password must contain 1 uppercase character.

### LoginFragment
This fragment displays when the user successfully completed the registration. The login fragment uses username & password validation for the user. It checks the values already stored in the database against the entered values.
If the user is already registered, the app will navigate the user to the login fragment.

### HomeFragment
Displays after successful login. The home fragment uses a lifecycle observer to track user's time spent on the foreground or background of the app.
Users can also log out from home fragment.

### Building
You can open the project in Android studio and press run.
Android Studio version used to build the project: 4.2.2

### Installation
While installing the .apk - please click install anyway, if the google play protect warning shows an unverified publisher.


### Libraries
Android Support Library

Android Architecture Components

Android Data Binding

Coroutine for database operation

Room for local database

Android navigation components

LiveData, Lifecycle components.

Mockito, Espresso, Junit for testing


# Testing
The app uses both unit & instrumentation tests.

RegisterViewModelTest.kt - Unit test for the registration rules.

UserDataBaseDaoTest.kt - Test class for testing the database access objects for user entity.

InstrumentationTest.kt - Instrumentation test using espresso. The test runs from the beginning of the app process & verifies the navigation through different actions. This test should be run on first run of the app.


# Custom Button
The app has a custom module called Custom Button, which is used across the main application.

Custom Button has the following properties:
a) Button title - show/hide/change color
b) Button subtitle - show/hide/change color
c) Button image - show/hide/tint color/Image size
d) Button ripple - color
e) Button background color
f) Button shape (rectange, circle, square, oval)
g) Button border - width/color
h) Button corner - radius

These attributes can be configured from layouts.xml

For example:
`<com.dev.custombutton.CustomButton
android:id="@+id/logoutBtn"
android:layout_width="250dp"
android:layout_height="50dp"
android:layout_marginBottom="50dp"
android:onClick="@{()->viewModel.logoutUser()}"
app:btn_backgroundColor="@color/light_blue"
app:btn_borderColor="@color/border_color"
app:btn_borderWidth="1dp"
app:btn_cornerRadius="10dp"
app:btn_image="@drawable/logout_icon"
app:btn_imageSize="40dp"
app:btn_imageTint="@color/white"
app:btn_imageVisible="true"
app:btn_rippleColor="@color/purple_700"
app:btn_subtitle="@string/end_session"
app:btn_subtitleColor="@color/white"
app:btn_title="@string/logout"
app:btn_titleColor="@color/white"/>`

![](/images/custom_button.png)

The attributes can also be configured from themes.xml

For example:
`<com.dev.custombutton.CustomButton
android:id="@+id/loginBtn"
style="?attr/button_style"
android:layout_width="200dp"
android:layout_height="50dp"
android:layout_marginTop="50dp"
android:onClick="@{()->viewModel.loginButton()}"
app:btn_subtitle="@string/username_password"
app:btn_title="@string/logintext"/>`

In themes.xml:
`<style name="CustomButtonStyle">
<item name="btn_titleColor">@color/white</item>
<item name="btn_subtitleColor">@color/white</item>
<item name="btn_imageTint">@color/white</item>
<item name="btn_backgroundColor">@color/light_blue</item>
<item name="btn_rippleColor">@color/purple_700</item>
<item name="btn_borderColor">@color/dark_blue</item>
<item name="btn_borderWidth">2dp</item>
<item name="btn_cornerRadius">10dp</item>
</style>`

Plug in the custom style to main app-theme:
`<style name="AppTheme" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
<item name="button_style">@style/CustomButtonStyle</item>
</style>`

