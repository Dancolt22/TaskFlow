// Import useState hook from React to manage local component state
import { useState } from "react";
// Import our API service function to send signup data to the backend
import { createSignup } from "../services/api.js";
// Import our reusable feedback Message component
import Message from "./Message.jsx";

/**
 * SignupForm Component
 * A controlled form component for registering a new user account.
 * Demonstrates React state, form event handling, validation, and API integration.
 */
function SignupForm() {
  // State for the full name input field
  const [fullName, setFullName] = useState("");
  // State for the email input field
  const [email, setEmail] = useState("");
  // State for the password input field
  const [password, setPassword] = useState("");

  // State to track if an API request is currently in flight (disables submit button)
  const [saving, setSaving] = useState(false);
  // State to store error messages returned from validation or the server
  const [error, setError] = useState("");
  // State to store success message once the user is created
  const [success, setSuccess] = useState("");

  // Handler function triggered when the user submits the form
  async function handleSubmit(event) {
    // Prevent the browser's default behavior of reloading the page on form submit
    event.preventDefault();

    // Reset previous error message before validating fresh input
    setError("");
    // Reset previous success message before validating fresh input
    setSuccess("");

    // Check that none of the required fields are left blank or contain only whitespace
    if (!fullName.trim() || !email.trim() || !password) {
      // Set an error message if any required field is empty
      setError("Fill in your name, email and password.");
      // Stop execution early
      return;
    }

    // Check that the password meets the minimum length requirement
    if (password.length < 8) {
      // Set an error message if the password is too short
      setError("Password must be at least 8 characters.");
      // Stop execution early
      return;
    }

    // Set saving to true to show loading state and prevent duplicate submissions
    setSaving(true);

    try {
      // Send the user registration data to the backend /api/signup endpoint
      await createSignup({ fullName, email, password });
      // Set the success message to confirm the account was registered
      setSuccess("Signup saved.");

      // Clear the full name input field upon successful submission
      setFullName("");
      // Clear the email input field upon successful submission
      setEmail("");
      // Clear the password input field upon successful submission
      setPassword("");
    } catch (err) {
      // Display the error message returned from the backend (e.g. email already registered)
      setError(err.message);
    } finally {
      // Turn off the saving loading state once the request completes
      setSaving(false);
    }
  }

  // Render the form JSX markup
  return (
    // Form element styled as a card; noValidate disables default browser tooltip validation
    <form className="card" onSubmit={handleSubmit} noValidate>
      {/* Form title heading */}
      <h2>Sign up</h2>

      {/* Label linked to the fullName input for accessibility */}
      <label htmlFor="fullName">Full name</label>
      {/* Controlled input: value comes from React state, updates via onChange */}
      <input
        // Connects input to the label's htmlFor="fullName"
        id="fullName"
        // Bound directly to the fullName state variable
        value={fullName}
        // Updates fullName state as the user types
        onChange={(e) => setFullName(e.target.value)}
        // Placeholder text demonstrating example format
        placeholder="Jane Doe"
      />

      {/* Label for email input */}
      <label htmlFor="email">Email address</label>
      {/* Controlled email input */}
      <input
        // Connects input to the label's htmlFor="email"
        id="email"
        // Specifies email input type for mobile keyboards
        type="email"
        // Bound directly to the email state variable
        value={email}
        // Updates email state as the user types
        onChange={(e) => setEmail(e.target.value)}
        // Placeholder text showing example email
        placeholder="jane@example.com"
      />

      {/* Label for password input */}
      <label htmlFor="password">Password</label>
      {/* Controlled password input with masked text entry */}
      <input
        // Connects input to the label's htmlFor="password"
        id="password"
        // Specifies password type to obscure entered characters
        type="password"
        // Bound directly to the password state variable
        value={password}
        // Updates password state as the user types
        onChange={(e) => setPassword(e.target.value)}
        // Helpful hint placeholder text
        placeholder="At least 8 characters"
      />

      {/* Render error banner if there is an error message */}
      <Message type="error" text={error} />
      {/* Render success banner if there is a success message */}
      <Message type="success" text={success} />

      {/* Submit button: disabled while saving to prevent duplicate submissions */}
      <button type="submit" disabled={saving}>
        {/* Dynamic button text based on whether saving is in progress */}
        {saving ? "Saving..." : "Create account"}
      </button>
    </form>
  );
}

// Export SignupForm so it can be imported and rendered in App.jsx
export default SignupForm;
