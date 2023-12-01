import portScanner.Host;

import java.util.List;
import java.util.Iterator;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
  public static void main(String[] args) {
    final InetAddress inetAddress;

    try {
      inetAddress = InetAddress.getLocalHost();
    }
    catch(final UnknownHostException unknownHostException) {
      System.out.println(unknownHostException);
      return;
    }

    final Host host = new Host(inetAddress);
    final List<Integer> openPortNumbers = host.openPortNumbersBetween(host.minimumPortNumber(), host.maximumPortNumber());
    final Iterator<Integer> openPortNumbersIterator = openPortNumbers.iterator();

    while(openPortNumbersIterator.hasNext()) {
      final int openPortNumber = openPortNumbersIterator.next();

      System.out.println(openPortNumber + " - " + host.portDescription(openPortNumber));
    }
  }
}
