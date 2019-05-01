package Ass2;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jengli on 03/03/2017.
 */
//class for the tree class which the expression is stored in
public class ExpTree
{
    private int kind;
    private static boolean rootWhere;
    private Object value;
    private static Map<Character, Integer> idMap = new TreeMap<>();
    private ExpTree lChild, rChild;
    public static final int numNode = 0, idNode = 1, opNode = 2, whereNode = 3, andNode = 4, equalsNode = 5;

    //takes information about the tree an constructs an ExpTree
    public ExpTree(int knd, Object val, ExpTree l,ExpTree r)
    {
        kind = knd; value = val;
        lChild = l; rChild = r;
    }

    //returns the value of the root
    public Object getValue()
    {
        return value;
    }


    //gets passed a boolean value and updates the static boolean inside ExpTree
    public void setRootWhere (boolean value)
    {
        rootWhere = value;
    }


    /**
     * output a string of the tree read in preOrder ( node, left, right)
     * @return
     */

    public String preOrder()
    {
        String output = value + " ";
        if(lChild != null) output += lChild.preOrder();
        if(rChild != null) output += rChild.preOrder();
        return output;
    }

    /**
     * returns the whole tree in order (left, node, right)
     * @return
     */
    @Override
    public String toString()
    {
        String output = "";
        if (value.equals('*') || value.equals('/') || value.equals('%') )
        {
            if (lChild.value.equals('+') || lChild.value.equals('-') )
            {
            output += "( ";
            }
        }

        if(lChild!=null){output += lChild.toString();}

        if (value.equals('*') || value.equals('/') || value.equals('%') )
        {
            if ( lChild.value.equals('+') || lChild.value.equals('-') )
            {
                output += ") ";
            }
        }
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        output = output + value + " ";
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        if (value.equals('*') || value.equals('/') || value.equals('%') )
        {
            if ( rChild.value.equals('+') || rChild.value.equals('-') || rChild.value.equals('*') || rChild.value.equals('/') || rChild.value.equals('%'))
            {
                output += "( ";
            }
        }

        else if (value.equals('+') || value.equals('-'))
        {
            if (rChild.value.equals('+') || rChild.value.equals('-'))
            {
                output += "( ";
            }
        }
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        if(rChild!=null){output+= rChild.toString();}
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        if (value.equals('*') || value.equals('/') || value.equals('%') )
        {
            if ( rChild.value.equals('+') || rChild.value.equals('-') || rChild.value.equals('*') || rChild.value.equals('/') || rChild.value.equals('%') )
            {
                output += ") ";
            }

        }
        else if (value.equals('+') || value.equals('-'))
        {
            if (rChild.value.equals('+') || rChild.value.equals('-'))
        {
            output += ") "; }
        }
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        return output;
    }

    /**
     * runs through the tree evaluating the arithmetic expressions recursively
     * @return
     */
    public int evaluate ()
    {
        int sum = 0;
        if (kind == 0)
        {
            return (int) value;
        }

        if (kind == 1)
        {
            if (rootWhere){
                if (idMap.containsKey(value)) {return idMap.get(value);}
                else
                {
                    System.out.println("You did not define a value for ID" + value.toString() + "it has been set to zero");
                    return 0;
                }
            }
            else
            {
                char temp = value.toString().toLowerCase().charAt(0);
                return (int) temp - 72 - (((int) temp - (int) 'a')*2);
            }
        }

        if (kind == 2)
        {
            switch (value.toString()) {
                case "*":   sum = lChild.evaluate() * rChild.evaluate();
                            break;
                case "-":   sum = lChild.evaluate() - rChild.evaluate();
                            break;
                case "+":   sum = lChild.evaluate() + rChild.evaluate();
                            break;
                case "/":   sum = lChild.evaluate() / rChild.evaluate();
                            if (rChild.evaluate() == 0)throw new ArithmeticException();
                            break;
                case "%":   sum = lChild.evaluate() % rChild.evaluate();
                            break;
            }
        }

        return sum;
    }


    /**
     * recursively adds to a map with the values of the where expression
     * @param where
     * @return
     */
    public Map<Character, Integer> getID (ExpTree where){
        if (where.kind == 5)
        {
            idMap.put((Character) where.lChild.value, where.rChild.evaluate());
        }
        if (where.lChild != null)getID(where.lChild);
        if (where.rChild != null)getID(where.rChild);

        return idMap;
    }

    /**
     * returns the right child
     * @return
     */
    public ExpTree returnRightChild () {
        return rChild;
    }

    /**
     * returns the left child
     * @return
     */
    public ExpTree returnLeftChild () {
        return lChild;
    }
}