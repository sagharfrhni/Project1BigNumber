import java.util.Arrays;

public class BigNumber {
    public int[] digits;
    private boolean isNegative;

    public BigNumber(String number) {
        isNegative = number.startsWith("-");
        if (isNegative) {
            number = number.substring(1);
        }
        digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = number.charAt(i) - '0';
        }
    }



    public BigNumber add(BigNumber other) {
        if (this.isNegative == other.isNegative) {
            BigNumber result = addAbsoluteValues(other);
            result.isNegative = this.isNegative;
            return result;
        } else {
            if (compareAbsolute(other) >= 0) {
                BigNumber result = subtractAbsoluteValues(other);
                result.isNegative = this.isNegative;
                return result;
            } else {
                BigNumber result = other.subtractAbsoluteValues(this);
                result.isNegative = other.isNegative;
                return result;
            }
        }
    }

    public BigNumber subtract(BigNumber other) {
        if (this.isNegative != other.isNegative) {
            BigNumber result = addAbsoluteValues(other);
            result.isNegative = this.isNegative;
            return result;
        } else {
            if (compareAbsolute(other) >= 0) {
                BigNumber result = subtractAbsoluteValues(other);
                result.isNegative = this.isNegative;
                return result;
            } else {
                BigNumber result = other.subtractAbsoluteValues(this);
                result.isNegative = !this.isNegative;
                return result;
            }
        }
    }

    public BigNumber multiply(BigNumber other) {
        BigNumber result = multiplyAbsoluteValues(other);
        result.isNegative = this.isNegative != other.isNegative;
        return result;
    }

    public BigNumber divide(BigNumber other) {
        if (other.compareAbsolute(new BigNumber("0")) == 0) {
            throw new ArithmeticException("Division by zero");
        }

        BigNumber quotient = new BigNumber("0");
        BigNumber remainder = new BigNumber(this.toString());

        while (remainder.compareAbsolute(other) >= 0) {
            remainder = remainder.subtractAbsoluteValues(other);
            quotient = quotient.add(new BigNumber("1"));
        }

        quotient.isNegative = this.isNegative != other.isNegative;
        return quotient;
    }
    public BigNumber power(int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative");
        }

        BigNumber result = new BigNumber("1");
        BigNumber base = new BigNumber(this.toString());

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = result.multiply(base);
            }
            base = base.multiply(base);
            exponent /= 2;
        }

        return result;
    }


    public BigNumber factorial() {
        BigNumber result = new BigNumber("1");
        BigNumber counter = new BigNumber("1");
        BigNumber one = new BigNumber("1");
        while (counter.compareAbsolute(this) <= 0) {
            result = result.multiply(counter);
            counter = counter.add(one);
        }
        return result;
    }

    private BigNumber addAbsoluteValues(BigNumber other) {
        int maxLength = Math.max(this.digits.length, other.digits.length);
        int[] result = new int[maxLength + 1];
        int carry = 0;

        for (int i = 0; i < maxLength; i++) {
            int sum = getDigitFromRight(i) + other.getDigitFromRight(i) + carry;
            result[result.length - 1 - i] = sum % 10;
            carry = sum / 10;
        }
        result[0] = carry;
        return new BigNumber(arrayToString(result));
    }

    private BigNumber subtractAbsoluteValues(BigNumber other) {
        int[] result = new int[digits.length];
        int borrow = 0;

        for (int i = digits.length - 1; i >= 0; i--) {
            int diff = getDigit(i) - other.getDigit(i) - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result[i] = diff;
        }
        return new BigNumber(arrayToString(result));
    }

    private BigNumber multiplyAbsoluteValues(BigNumber other) {
        int[] result = new int[digits.length + other.digits.length];
        for (int i = digits.length - 1; i >= 0; i--) {
            int carry = 0;
            for (int j = other.digits.length - 1; j >= 0; j--) {
                int product = result[i + j + 1] + getDigit(i) * other.getDigit(j) + carry;
                result[i + j + 1] = product % 10;
                carry = product / 10;
            }
            result[i] += carry;
        }
        return new BigNumber(arrayToString(result));
    }

    private int getDigit(int index) {
        return index < digits.length ? digits[index] : 0;
    }

    private int getDigitFromRight(int index) {
        int reversedIndex = digits.length - 1 - index;
        return getDigit(reversedIndex);
    }

    private int compareAbsolute(BigNumber other) {
        if (this.digits.length != other.digits.length) {
            return this.digits.length - other.digits.length;
        }
        for (int i = 0; i < digits.length; i++) {
            if (this.digits[i] != other.digits[i]) {
                return this.digits[i] - other.digits[i];
            }
        }
        return 0;
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        int start = 0;
        while (start < array.length && array[start] == 0) {
            start++;
        }
        for (int i = start; i < array.length; i++) {
            sb.append(array[i]);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append("-");
        }
        for (int digit : digits) {
            sb.append(digit);
        }
        return sb.toString();
    }
    public BigNumber leftShift(int times) {
        if (times <= 0) return new BigNumber(this.toString());
        int[] shiftedDigits = new int[this.digits.length + times];
        System.arraycopy(this.digits, 0, shiftedDigits, times, this.digits.length);

        return new BigNumber(arrayToString(shiftedDigits));
    }
    public static String shiftLeft(String number, int shiftAmount) {
        for (int i = 0; i < shiftAmount; i++) {
            number = number + '0';
        }
        return number;
    }
    public static String shiftRight(String number, int shiftAmount) {
        for (int i = 0; i < shiftAmount; i++) {
            number = '0' + number.substring(0, number.length() - 1);
        }
        return number;
    }

    public BigNumber karatsuba(BigNumber other) {
        // Base case for recursion
        if (this.digits.length < 10 && other.digits.length < 10) {
            return this.multiply(other);
        }

        // Determine the length of the numbers
        int n = Math.max(this.digits.length, other.digits.length);
        int half = n / 2;

        // Split this number into two halves
        int[] leftPartA = Arrays.copyOfRange(this.digits, 0, Math.min(half, this.digits.length));
        int[] rightPartA = Arrays.copyOfRange(this.digits, Math.min(half, this.digits.length), this.digits.length);

        // Split other number into two halves
        int[] leftPartB = Arrays.copyOfRange(other.digits, 0, Math.min(half, other.digits.length));
        int[] rightPartB = Arrays.copyOfRange(other.digits, Math.min(half, other.digits.length), other.digits.length);

        // Create BigNumber instances from the parts
        BigNumber A = new BigNumber(arrayToString(leftPartA));
        BigNumber B = new BigNumber(arrayToString(rightPartA));
        BigNumber C = new BigNumber(arrayToString(leftPartB));
        BigNumber D = new BigNumber(arrayToString(rightPartB));

        // Recursively calculate the products
        BigNumber ac = A.karatsuba(C);
        BigNumber bd = B.karatsuba(D);
        BigNumber midTerm = (A.add(B)).karatsuba(C.add(D));

        // Use the Karatsuba formula to combine results
        BigNumber crossTerm = midTerm.subtract(ac).subtract(bd);

        // Shift and combine the results
        return ac.leftShift(n).add(crossTerm.leftShift(half)).add(bd);
    }

}