//Calculator.java
/*
* This program uses Swing to build a caluclator
* graphical user interface.
*
* @author Caitlin (Burke) Vinci
* @version 27.0 last_modified 04_13_16
*
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


/*
* The caluclator class builds a calculator and implements
* each function by using button listeners and then calling
* methods.
*
*/
public class Calculator extends JFrame implements ActionListener
{
    private Container container;
    private JTextField display;
    private JButton [] keys;
    private String result;
    private String typed;
    private int oprnd;
    private double stdnum, res, holdnum;
    private boolean decimalPressed, displayIsZero, negativeNum;


    final int MAX_INPUT = 10;

    @SuppressWarnings("unchecked")
    /*
    * Calculator constructor uses a gridlayout to
    * set the buttons and text area in the correct locations,
    * while setting colors and fonts. It also adds ActionListeners
    *
    */
    public Calculator ()
    {
        setTitle( "Calculator" );
        setSize( 600, 700 );
        setLocation( 100, 100 );
        setDefaultCloseOperation( EXIT_ON_CLOSE );

        typed = "";
        oprnd = 0;
        stdnum = 0;
        res = 0;
        holdnum = 0;
        result = "";
        decimalPressed = false;
        displayIsZero = true;
        negativeNum = false;

        Font f = new Font ("Arial", Font.BOLD, 15);
        Font t = new Font ("Arial", Font.BOLD, 25);

        typed = "0";
        displayIsZero = true;
        display = new JTextField("0");
        display.setFont(t);
        display.addActionListener(this);
        display.setHorizontalAlignment(JTextField.RIGHT);


        keys = new JButton [20];
        keys[0] = new JButton("0");
        keys[1] = new JButton("1");
        keys[2] = new JButton("2");
        keys[3] = new JButton("3");
        keys[4] = new JButton("4");
        keys[5] = new JButton("5");
        keys[6] = new JButton("6");
        keys[7] = new JButton("7");
        keys[8] = new JButton("8");
        keys[9] = new JButton("9");
        keys[10] = new JButton("C");
        keys[11] = new JButton("SQRT");
        keys[12] = new JButton("/");
        keys[13] = new JButton("*");
        keys[14] = new JButton("-");
        keys[15] = new JButton("+");
        keys[16] = new JButton("=");
        keys[17] = new JButton(".");
        keys[18] = new JButton("+/-");
        keys[19] = new JButton("X^2");

        JPanel buttons = new JPanel();
        buttons.setLayout (new GridLayout(5, 4));
        ButtonListener b = new ButtonListener();
        //All buttons
        for ( JButton j : keys) j.setFont(f);
        for ( JButton j : keys) j.addActionListener(b);
        //for ( JButton j : keys) buttons.add(j);
        buttons.add(keys[10]);
        buttons.add(keys[11]);
        buttons.add(keys[12]);
        buttons.add(keys[13]);
        buttons.add(keys[7]);
        buttons.add(keys[8]);
        buttons.add(keys[9]);
        buttons.add(keys[14]);
        buttons.add(keys[4]);
        buttons.add(keys[5]);
        buttons.add(keys[6]);
        buttons.add(keys[15]);
        buttons.add(keys[1]);
        buttons.add(keys[2]);
        buttons.add(keys[3]);
        buttons.add(keys[16]);
        buttons.add(keys[18]);
        buttons.add(keys[0]);
        buttons.add(keys[19]);
        buttons.add(keys[17]);

        for (int j = 10; j < keys.length; j++)
        {
            keys[j].setBackground(Color.CYAN);
        }

        //Number buttons only
        for (int i = 0; i < 10; i++)
        {
            keys[i].setBackground(Color.ORANGE);
        }

        Container container = getContentPane();
        container.add(display, BorderLayout.NORTH);
        container.add(buttons, BorderLayout.CENTER);


        setVisible(true);

    }

    /*
    * This actionPerformed method is here to be overwritten
    * by the inner class below.
    */
    public void actionPerformed (ActionEvent e)
    {

    }

    /*
    * The inner class ButtonListener listens forms
    * buttons to be clicked.
    *
    */
    class ButtonListener implements ActionListener
    {
        /*
        * The actionPerformed method uses a switch call
        * to call methods depending on which button was
        * pushed.
        *
        *@param e  the button action event that was pressed.
        *
        */
        public void actionPerformed (ActionEvent e)
        {
            int button = 0;
            for (int i = 0; i < keys.length; i++)
            {
                if (e.getSource() == keys[i]) {button = i;}
            }

            switch (button)
            {
                case 0: addZero(); break;
                case 1: case 2: case 3: case 4: case 5:
                case 6: case 7: case 8: case 9: addDigit(button); break;
                case 10: clear(); break;
                case 11: squareRoot(); break;
                case 12: case 13: case 14: case 15: processOp(button); break;
                case 16: processEquals(); break;
                case 17: addDecimal(); break;
                case 18: changePosNeg (); break;
                case 19: squaredX(); break;
                default: break;
            }
       }
    }

    /*
    * addDigit is called when a number Jbutton has been
    * pressed. It takes into account whether this digit
    * is the first number in the display, or if it's a
    * negative number. If there are already numbers in the
    * display, the new number is concatenated to the typed
    * String.
    *
    * @param d  the number corresponding to the button pushed
    *
    */
    public void addDigit (int d)
    {
        if (displayIsZero)
        {
            if (negativeNum)
            {
                typed = "-" + d;
            }
            else
            {
                typed = "" + d;
            }
            stdnum = Double.parseDouble(typed);
            display.setText(typed);
            displayIsZero = false;
        }
        else
        {
            typed = typed + d;
            display.setText(typed);
            stdnum = Double.parseDouble(typed);
            displayIsZero = false;
        }
    }
    /*
    * addZero is a boolean that allows for a zero to be added
    * to the display. This only is allowed if the zero comes
    * after another digit, or after a decimal. This prevents
    * a string of zeros from being entered.
    *
    */
    public void addZero ()
    {
        if (!displayIsZero)
        {
            typed = typed + '0';
            display.setText(typed);
            stdnum = Double.parseDouble(typed);
        }
    }
    /*
    * addDecimal is initiated when the decimal Jbutton
    * is pushed. If there were no digits before the decimal
    * point, a zero is added.
    *
    */
    public void addDecimal ()
    {
        if (displayIsZero)
        {
            typed = "0.";
            displayIsZero = false;
            display.setText(typed);
        }
        else
        {
            typed = typed + '.';
            display.setText(typed);
            decimalPressed = true;
        }
    }
    /*
    * changePosNeg is initiated when the +/- button is
    * pushed. If there was already a number in the display,
    * then the negative is added to the beginning of the
    * display string. If the number was already negative,
    * then the number is turned back to positive.
    *
    *
    */
    public void changePosNeg ()
    {
        if (negativeNum)
        {
            stdnum = (stdnum * -1);
            if (stdnum % 1 == 0){typed = "" + ((int)(stdnum));}
            else {typed = "" + stdnum;}
            display.setText(typed);
            negativeNum = false;
            decimalPressed = true;
        }
        else
        {
            negativeNum = true;
            stdnum = (stdnum * -1);
            if (stdnum % 1 == 0) {typed = "" + ((int)(stdnum));}
            else {typed = "" + stdnum;}
            display.setText(typed);
        }
    }

    /*
    * clear is initiated when the C button is clicked.
    * this method clears out all variables and strings
    * so that the calculator can start over.
    *
    */
    public void clear ()
    {
        typed = "0";
        display.setText(typed);
        result = "";
        displayIsZero = true;
        stdnum = 0;
        holdnum = 0;
        res = 0;
        oprnd = 0;
        negativeNum = false;
        decimalPressed = false;
    }

    /*
    * multiply performs multiplication with local variables
    *
    * @param multiplicand - the stdnum from the processOp method
    * @param multiplier - the holdnum from the processOp method
    *
    * @return product - the result
    */
    public double multiply (double multiplicand, double multiplier)
    {
        double product = (multiplicand * multiplier);
        return product;
    }
    /*
    * divide performs division with local variables
    *
    * @param dividend - the stdnum from the processOp method
    * @param divisor - the holdnum from the processOp method
    *
    * @return quotient - the result
    */
    public double divide (double dividend, double divisor)
    {
        double quotient = (dividend / divisor);
        return quotient;
    }
    /*
    * addition performs addition with local variables
    *
    * @param augend - the stdnum from the processOp method
    * @param addend - the holdnum from the processOp method
    *
    * @return sum - the result
    */
    public double addition (double augend, double addend)
    {
        double sum = (augend + addend);
        return sum;
    }
    /*
    * subtraction performs subtraction with local variables
    *
    * @param minuend - the stdnum from the processOp method
    * @param subtrahend - the holdnum from the processOp method
    *
    * @return difference - the result
    */
    public double subtraction (double minuend, double subtrahend)
    {
        double difference = (minuend - subtrahend);
        return difference;
    }

    /*
    * processOp uses the button number location to assign
    * an opperation method. If this is the first opperation
    * button pressed in the calculation, the operation call
    * is held until the equals button is pressed.
    *
    * @param op - the opperation button number
    *
    */

    public void processOp (int op)
    {
        oprnd = op;

        if (holdnum != 0)
        {
            if (oprnd == 15)  //addition
            {
                res = addition(holdnum, stdnum);
                display.setText("" + res);

            }
            if (oprnd == 14) //subtraction
            {
                res = subtraction(holdnum, stdnum);
                display.setText("" + res);
            }
            if (oprnd == 13) //multiplication
            {
                res = multiply(holdnum, stdnum);
                display.setText("" + res);
            }
            if (oprnd == 12) //division
            {
                if (stdnum != 0)
                {
                    res = divide(holdnum, stdnum);
                    display.setText("" + res);
                }
                else {giveErrorMsg();}
            }
            holdnum = stdnum;
            stdnum = 0;
            displayIsZero = true;
        }
        else
        {
            holdnum = stdnum;
            stdnum = 0;
            displayIsZero = true;
            negativeNum = false;
            typed = "";
        }
    }
    /*
    * SquareRoot gives the square root of the stored number
    * currently in the display
    *
    */
    public void squareRoot()
    {
        res = Math.sqrt(stdnum);
        display.setText("" + res);
        displayIsZero = true;
        holdnum = 0;
        stdnum = res;
    }
    /*
    * processEquals is called when the equals buttons
    * is pressed. It uses the oprnd variable to pass
    * the stored values into the operation methods, then
    * changes the display to reflect the result.
    *
    */
    public void processEquals()
    {
        if (holdnum != 0)
        {
            if (oprnd == 15)  //addition
            {
                res = addition(holdnum, stdnum);
                display.setText("" + String.valueOf(res));

            }
            if (oprnd == 14) //subtraction
            {
                res = subtraction(holdnum, stdnum);
                display.setText("" + String.valueOf(res));
            }
            if (oprnd == 13) //multiplication
            {
                res = multiply(holdnum, stdnum);
                display.setText("" + String.valueOf(res));
            }
            if (oprnd == 12) //division
            {
                if (stdnum != 0)
                {
                    res = divide(holdnum, stdnum);
                    display.setText("" + String.valueOf(res));
                } else {giveErrorMsg();}
            }
            result = String.valueOf(res);
            display.setText(result);
            stdnum = res;
            holdnum = 0;
            oprnd = 0;
            displayIsZero = true;
        }
        else res = stdnum;
        display.setText("" + res);
    }
    /*
    * squaredX takes the stored number and multiplies it
    * with itself to get the squared value. Then the result
    * is dislayed.
    *
    */
    public void squaredX()
    {
        res = stdnum * stdnum;
        display.setText("" + res);
        displayIsZero = true;
        holdnum = 0;
        stdnum = res;
    }
    /*
    * giveErrorMsg is supposed to be triggered if the
    * user tries to divide by zero, but it doesn't seem
    * to get called. I haven't found the reason for this yet.
    *
    */
    public void giveErrorMsg()
    {
        display.setText("ERROR");
    }


    public static void main (String [] args)
    {
      Calculator calc = new Calculator();
    }
}
