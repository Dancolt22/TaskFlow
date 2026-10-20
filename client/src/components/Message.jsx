/**
 * Message Component
 * A reusable feedback banner that displays either an error or a success message.
 * If no text is provided, it renders nothing at all.
 * @param {string} type - Message style category: "error" or "success"
 * @param {string} text - The message text content to display
 */
function Message({ type, text }) {
  // If there is no message text to show, return null so React renders nothing into the DOM
  if (!text) return null;

  // Render the paragraph element with the appropriate style and accessibility role
  return (
    <p
      // Apply the base "message" class along with "error" or "success" for color styling
      className={`message ${type}`}
      // Screen readers announce errors immediately with "alert", or politely with "status" for success
      role={type === "error" ? "alert" : "status"}
    >
      {/* Display the message text inside the banner */}
      {text}
    </p>
  );
}

// Export Message so it can be imported and reused in any form or component
export default Message;
