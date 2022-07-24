package com.samz.convertcurrency

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import org.mockito.ArgumentMatcher
import org.mockito.internal.matchers.ContainsExtraTypeInfo
import org.mockito.internal.matchers.text.ValuePrinter
import java.io.Serializable
import java.util.*


class BundleEquals(@field:Nullable @param:Nullable private val expected: Bundle?) :
    ArgumentMatcher<Bundle?>,
    ContainsExtraTypeInfo, Serializable {

    override fun matches(actual: Bundle?): Boolean {
        if (expected == null && actual == null) {
            return true
        }
        return if (expected == null || actual == null) {
            false
        } else areBundlesEqual(expected, actual)
    }

    private fun areBundlesEqual(@NonNull expected: Bundle, @NonNull actual: Bundle): Boolean {
        if (expected.size() != actual.size()) {
            return false
        }
        if (!expected.keySet().containsAll(actual.keySet())) {
            return false
        }
        for (key in expected.keySet()) {
            @Nullable val expectedValue = expected[key]
            @Nullable val actualValue = actual[key]
            if (expectedValue is Bundle && actualValue is Bundle) {
                if (!areBundlesEqual(expectedValue, actualValue)) {
                    return false
                }
            } else if (!Objects.equals(expectedValue, actualValue)) {
                return false
            }
        }
        return true
    }

    override fun toStringWithType(): String {
        val clazz = expected?.javaClass?.simpleName
        return "(" + clazz + ") " + describe(expected)
    }

    private fun describe(`object`: Any?): String {
        return ValuePrinter.print(`object`)
    }

    override fun typeMatches(actual: Any): Boolean {
        return expected != null && actual.javaClass == expected.javaClass
    }

    override fun toString(): String {
        return describe(expected)
    }
}