package expression;

/**
 * Created by yf on 2019/2/18.
 */


public class ExpressionValue {

    private double mValue;
    private boolean mCorrect;
    private String mexp;
    private int mIndex;

    public ExpressionValue(String mexp) {

        this.mexp = mexp.replaceAll(" ", "").replaceAll("pi", "" + Math.PI).replaceAll("e", "" + Math.E);
        mCorrect = true;
        mValue = 0;
        mIndex = 0;
        this.mValue = this.doubleValue();
    }

    private double doubleValue() {
        skip();
        double value1 = term();

        while (this.mIndex < this.mexp.length() && mCorrect) {
            char ch = mexp.charAt(mIndex);
            if (ch == '+' || ch == '-') {
                this.mIndex++;
                double value2 = term();
                switch (ch) {
                    case '+':
                        value1 += value2;
                        break;
                    case '-':
                        value1 -= value2;
                        break;
                }
            } else if (ch == ')' || ch == ',')
                break;
                // else if(ch==' ')mIndex++;
            else {
                mCorrect = false;
                return 0;
            }
        }
        return value1;
    }

    private double term() {
        skip();
        char ch;
        double value1 = factor();
        while (this.mIndex < this.mexp.length() && mCorrect) {
            ch = mexp.charAt(mIndex);
            if (ch == '*' || ch == '/' || ch == '%') {
                this.mIndex++;
                double value2 = factor();
                switch (ch) {
                    case '*':
                        value1 *= value2;
                        break;
                    case '/':
                        value1 /= value2;
                        break;
                    case '%':
                        value1 = value1 % value2;
                        break;
                }
            }
            // else if(ch==' ')mIndex++;
            else
                break;
        }
        return value1;
    }

    private double factor() {
        skip();
        if (mexp.charAt(mIndex) == '(' && mCorrect) {
            mIndex++;
            double value = doubleValue();
            this.mIndex++;
            return value;
        } else if (mexp.charAt(mIndex) >= 'a' && mexp.charAt(mIndex) <= 'z' && mCorrect)
            return functionmath();
        return constant();

    }

    double functionmath() {

        int i = 0;

        while (mexp.charAt(mIndex + i) != '(')
            i++;
        String ch = mexp.substring(mIndex, mIndex + i);
        mIndex = mIndex + i + 1;
        skip();

        double value1 = doubleValue();
        if (ch.equals("abs"))
            value1 = Math.abs(value1);
        else if (ch.equals("asin"))
            value1 = Math.asin(value1);
        else if (ch.equals("atan"))
            value1 = Math.atan(value1);
        else if (ch.equals("cos"))
            value1 = Math.cos(value1);
        else if (ch.equals("cosh"))
            value1 = Math.cosh(value1);
        else if (ch.equals("ln"))
            value1 = Math.log(value1);
        else if (ch.equals("lg"))
            value1 = Math.log10(value1);
        else if (ch.equals("sin"))
            value1 = Math.sin(value1);
        else if (ch.equals("sinh"))
            value1 = Math.sinh(value1);
        else if (ch.equals("tan"))
            value1 = Math.tan(value1);
        else if (ch.equals("tanh"))
            value1 = Math.tanh(value1);
        else if (ch.equals("max")) {
            skip();
            if (mexp.charAt(mIndex) == ',')
                mIndex++;
            else {
                mCorrect = false;
                return 0;
            }
            skip();
            double value2 = doubleValue();
            // cout<<value2;
            value1 = Math.max(value1, value2);
        } else if (ch.equals("min")) {
            skip();
            if (mexp.charAt(mIndex) == ',')
                mIndex++;
            else {
                mCorrect = false;
                return 0;
            }

            skip();
            double value2 = doubleValue();
            // cout<<value2;
            value1 = Math.min(value1, value2);
        } else if (ch.equals("pow")) {
            skip();
            if (mexp.charAt(mIndex) == ',')
                mIndex++;
            else {
                mCorrect = false;
                return 0;
            }

            skip();
            double value2 = doubleValue();
            // cout<<value2;
            value1 = Math.pow(value1, value2);
        } else {
            mCorrect = false;
            return 0;
        }
        mIndex++;
        return value1;

    }

    private double constant() {
        if (mIndex < mexp.length()) {
            char ch = mexp.charAt(mIndex);
            int sign = 1;
            if (ch == '+' || ch == '-') {
                sign = ch == '-' ? -1 : 1;
                mIndex++;
            }
            skip();
            double value = 0;
            boolean isDec = false;  //小数
            int decCount = -1;      ////小数位数
            while (this.mIndex < mexp.length()
                    && (mexp.charAt(mIndex) <= '9' && mexp.charAt(mIndex) >= '0' || mexp.charAt(mIndex) == '.')) {
                if (mexp.charAt(mIndex) == '.') {
                    isDec = true;
                    decCount = -1;
                    mIndex++;
                } else {
                    if (!isDec)
                        value = value * 10 + mexp.charAt(mIndex++) - '0';
                    else
                        value += (mexp.charAt(mIndex++) - '0') * Math.pow(10.0, decCount--);
                }
            }
            return value * sign;
        }
        // mCorrect=false;
        return 0;
    }

    private void skip() {
        // while(mexp.charAt(mIndex)==' ')mIndex++;

    }

    public double getValue() {

        if (this.mCorrect)
            return this.mValue;
        else {
            System.out.print("error");
            return 0;
        }
    }

    public boolean isCorrect() {
        return this.mCorrect;

    }

    public void changeExpression(String s) {
        mexp = s.replaceAll(" ", "").replaceAll("pi", "" + Math.PI).replaceAll("e", "" + Math.E);
        mCorrect = true;
        mIndex = 0;
        this.mValue = this.doubleValue();

    }

    public String getMexp() {
        return mexp;
    }

    public static void main(String args[]){
        ExpressionValue expressionValue=new ExpressionValue("10+sin(pi)");
        System.out.println(expressionValue.getMexp()+"="+expressionValue.getValue());

    }
}
