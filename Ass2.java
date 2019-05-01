package Ass2;

public class Ass2 {

    //executes the code
    public static void main(String[] args)
    {
        String result = "";
        System.out.println("Jason English 1404665");
        System.out.println("Welcome to Jason's expression evaluation program ");
        while (!result.equals("n"))
        {
            System.out.println("Please type an expression ");
            Parser p = new Parser();
            try {
                ExpTree myTree = p.parseLine();
                if (myTree.getValue() == "where") {
                    myTree.setRootWhere(true);
                    myTree.getID(myTree.returnRightChild());
                    System.out.println("Pre-order: " + myTree.returnLeftChild().preOrder());
                    System.out.println("Result: " + myTree.returnLeftChild().evaluate());
                } else {
                    myTree.setRootWhere(false);
                    System.out.println("Pre-order: " + myTree.preOrder());
                    System.out.println("Result: " + myTree.evaluate());
                }
                System.out.println("In-order: " + myTree);
                System.out.println("Would you like to continue ? ");
                result = p.getLine();
            }
            catch (Exception e)
            {
                if (e instanceof ParseException) System.out.println(e);
                if (e instanceof ArithmeticException) System.out.println(e);
            }
        }

    }
}
