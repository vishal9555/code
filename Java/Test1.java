class Outer
{
    private int x=10;
    class Inner
    {
        private int y=20;
        void display()
        {
            System.out.println("x= "+x+"y= "+y);
        }
    }
    void display()
    {
        Inner i=new Inner();
        i.display();
        System.out.println("y= "+i.y);
    }
}
public class Test1 
{
    public static void main(String[] args) 
    {
        Outer o=new Outer();
        Outer.Inner obj=o.new Inner();
        o.display();
        obj.display();
    }
}
