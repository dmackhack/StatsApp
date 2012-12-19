import java.net.ServerSocket;

public class DummyServiceStarter
{

    public static void main(String[] args) throws Exception
    {

        if (args.length != 1)
        {
            System.out.println("Invalid number of arguments: " + String.valueOf(args.length));

        }
        else
        {
            System.out.println("Starting service on port [" + String.valueOf(args[0]) + "]");
            ServerSocket socket = null;
            try
            {
                socket = new ServerSocket(Integer.parseInt(args[0]));
                while (true)
                {
                }
            }
            finally
            {
                if (socket != null)
                {
                    socket.close();
                }
            }
        }
    }
}
