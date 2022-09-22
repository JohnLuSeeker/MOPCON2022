package com.example.compose.jetsurvey.signinsignup

import java.util.regex.Pattern

// Consider a verification code valid
private const val VERIFICATION_CODE_VALIDATION_REGEX = "^\\d{6}\$"

class VerificationCodeState :
    TextFieldState(validator = ::isCodeValid, errorFor = ::codeValidationError)

/**
 * Returns an error to be displayed or null if no error was found
 */
private fun codeValidationError(email: String): String {
    return "Invalid code"
}

private fun isCodeValid(code: String): Boolean {
    return Pattern.matches(VERIFICATION_CODE_VALIDATION_REGEX, code)
}