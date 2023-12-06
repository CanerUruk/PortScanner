package portScanner;


import java.util.List;
import java.util.LinkedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.swing.JFrame;

import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Host {
  public Host(final InetAddress inetAddress) {
    this.inetAddress = inetAddress;
  }
  public List<Integer> openPortNumbersBetween(final int firstPortNumber, final int lastPortNumber) throws IllegalArgumentException {
    if(firstPortNumber < this.minimumPortNumber() || firstPortNumber > this.maximumPortNumber() ||
       lastPortNumber < this.minimumPortNumber() || lastPortNumber > this.maximumPortNumber()) {
      throw new IllegalArgumentException("Port number out of range! It should be between [" + this.minimumPortNumber() + ", " + this.maximumPortNumber() + "]");
    }

    final ExecutorService executorService = Executors.newFixedThreadPool(10);
    final List<Future<Integer>> possibleOpenPortNumbers = new LinkedList<Future<Integer>>();

    for(int portNumber = firstPortNumber; portNumber <= lastPortNumber; portNumber++) {
      final int finalPortNumber = portNumber;
      possibleOpenPortNumbers.add(executorService.submit(() -> {
        try {
          final Socket socket = new Socket();
          socket.connect(new InetSocketAddress(this.inetAddress, finalPortNumber), 2000);
          socket.close();
          return finalPortNumber;
        } catch(final IOException ioException) {
          System.out.println(ioException);
          return -1;
        }
      }));
    }

    final List<Integer> openPortNumbers = new LinkedList<Integer>();
    for(int possibleOpenPortNumbersIndex = 0; possibleOpenPortNumbersIndex < possibleOpenPortNumbers.size(); possibleOpenPortNumbersIndex++) {
      try{
        if(possibleOpenPortNumbers.get(possibleOpenPortNumbersIndex).get() == -1) {
          continue;
        }

        openPortNumbers.add(possibleOpenPortNumbers.get(possibleOpenPortNumbersIndex).get());
      }
      catch(final InterruptedException interruptedException) {
        System.out.println(interruptedException);
      }
      catch(final ExecutionException executionException) {
        System.out.println(executionException);
      }
    }

    executorService.shutdown();

    return openPortNumbers;
  }

  public int minimumPortNumber() {
    return 0;
  }

  public int maximumPortNumber() {
    return 10;
  }

  public String portDescription(final int portNumber) {
	  switch(portNumber) {
      case 1:
        return "TCP Port Service Multiplexer (TCPMUX)";
      case 2:
        return "Management Utility";
      case 3:
        return "Compression Process";
      // Diğer port numaralarını ve açıklamalarını burada ekleyin...
      case 80:
        return "Hypertext Transfer Protocol (HTTP)";
      case 443:
        return "Hypertext Transfer Protocol over TLS/SSL (HTTPS)";
      case 3306:
        return "MySQL Database";
      case 8080:
        return "HTTP Proxy";
      // Diğer port numaralarını ve açıklamalarını burada ekleyin...
      default:
        return "No port description";
    }
  }

  private final InetAddress inetAddress;
}
