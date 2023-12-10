package lynorg.filemngt.common;

public class NumberToWordsConverter {

    private static final String[] units = {"", " Thousand", " Million", " Billion", " Trillion"};

    private static final String[] ones = {"", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten",
            " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"};

    private static final String[] tens = {"", "", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};

    private static final int BILLION = 1000000000;
    private static final int MILLION = 1000000;
    private static final int THOUSAND = 1000;
    private static final int HUNDRED = 100;

    public static String numberToWordsConverter(double amount, String mainCurrency, String floatingCurrency) {
        if (amount == 0) {
            return "Zero " + mainCurrency;
        }

        long dollars = (long) amount;
        int cents = (int) ((amount - dollars) * 100);

        String dollarsInWords = convert(dollars);
        String centsInWords = convert(cents);

        StringBuilder result = new StringBuilder(dollarsInWords + " " + mainCurrency);

        if (cents > 0) {
            result.append(" and ").append(centsInWords).append(" ").append(floatingCurrency);
        }

        return result.toString();
    }

    private static String convert(long n) {
        if (n == 0) {
            return "";
        }

        StringBuilder current = new StringBuilder();
        int index = 0;

        do {
            long num = n % 1000;
            if (num != 0) {
                String str = convertLessThanOneThousand(num);
                current.insert(0, str + units[index]);
            }
            index++;
            n /= 1000;
        } while (n > 0);

        return current.toString().trim();
    }

    private static String convertLessThanOneThousand(long n) {
        StringBuilder current = new StringBuilder();

        if (n % HUNDRED < 20) {
            current.append(ones[(int) (n % HUNDRED)]);
            n /= 100;
        } else {
            current.append(ones[(int) (n % 10)]);
            n /= 10;

            current.insert(0, tens[(int) (n % 10)]);
            n /= 10;
        }

        if (n == 0) {
            return current.toString();
        }

        current.insert(0, ones[(int) n] + " Hundred ");
        return current.toString();
    }
}
