package portScanner;

import java.util.List;
import java.util.LinkedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    return 65535;
  }

  public String portDescription(final int portNumber) {
    switch(portNumber)
    {
      case 1:
				return "TCP Port Service Multiplexer (TCPMUX)";
      case 2:
				return "CompressNET Management Utility";
      case 3:
				return "CompressNET Compression Process";
      case 5:
				return "Remote job entry";
      case 7:
				return "Echo Protocol";
      case 9:
				return "Discard Protocol";
      case 11:
				return "Active Users (systat service)";
      case 13:
				return "Daytime Protocol (RFC 867)";
      case 17:
				return "Quote Of The Day";
      case 19:
				return "Character Generator Protocol";
      case 20:
				return "FTP data transfer";
      case 21:
				return "FTP control (command)";
      case 22:
				return "Secure Shell (SSH), secure logins, file transfers (SCP, SFTP)";
      case 23:
				return "Telnet protocol - unencrypted text communications";
      case 24:
				return "Priv-mail, any private mail system";
      case 25:
				return "Simple Mail Transfer Protocol (SMTP)";
      case 27:
				return "NSW User System FE";
      case 29:
				return "MSG ICP";
      case 33:
				return "Display Support Protocol";
      case 35:
				return "Any private printer server protocol";
      case 37:
				return "Time Protocol";
      case 39:
				return "Resource Location Protocol (RLP)";
      case 42:
				return "ARPA Host Name Server Protocol";
      case 43:
				return "WHOIS protocol";
      case 47:
				return "NI FTP";
      case 49:
				return "TACACS+ Login Host protocol";
      case 50:
				return "Remote Mail Checking Protocol";
      case 51:
				return "IMP Logical Address Maintenance";
      case 52:
				return "XNS (Xerox Network Systems) Time Protocol";
      case 53:
				return "Domain Name System (DNS)";
      case 54:
				return "Xerox Network Systems (XNS) clearinghouse";
      case 55:
				return "ISI Graphics Language (ISI-GL)";
      case 56:
				return "Xerox Network Systems (XNS) authentication";
      case 57:
				return "Any private terminal access";
      case 58:
				return "Xerox Network Systems (XNS) Mail";
      case 64:
				return "CI (Travelport) (formerly Covia) Comms Integrator";
      case 67:
				return "Bootstrap Protocol (BOOTP) server or Dynamic Host Configuration Protocol (DHCP)";
      case 68:
				return "Bootstrap Protocol (BOOTP) client or Dynamic Host Configuration Protocol (DHCP)";
      case 69:
				return "Trivial File Transfer Protocol (TFTP)";
      case 70:
				return "Gopher protocol";
      case 71:
				return "NETRJS protocol";
      case 72:
				return "NETRJS protocol";
      case 73:
				return "NETRJS protocol";
      case 74:
				return "NETRJS protocol";
      case 77:
				return "Any private Remote job entry";
      case 79:
				return "Finger protocol";
      case 80:
				return "Hypertext Transfer Protocol (HTTP)";
      case 88:
				return "Kerberos authentication system";
      case 90:
				return "DNSIX Security Attribute Token Map";
      case 101:
				return "NIC host name";
      case 102:
				return "ISO Transport Service Access Point (TSAP)";
      case 104:
				return "ACR/NEMA Digital Imaging and Communications in Medicine (DICOM)";
      case 105:
				return "CCSO Nameserver Protocol (Qi/Ph)";
      case 107:
				return "Remote Telnet Service protocol";
      case 108:
				return "SNA Gateway Access Server";
      case 109:
				return "Post Office Protocol v2 (POP2)";
      case 110:
				return "Post Office Protocol v3 (POP3)";
      case 111:
				return "ONC RPC (Sun RPC)";
      case 113:
				return "Ident, authentication service/identification protocol";
      case 115:
				return "Simple File Transfer Protocol";
      case 118:
				return "Structured Query Language (SQL) Services";
      case 119:
				return "Network News Transfer Protocol (NNTP)";
      case 123:
				return "Network Time Protocol (NTP)";
      case 126:
				return "Formerly Unisys Unitary Login, renamed by Unisys to NXEdit";
      case 135:
				return "DCE endpoint resolution";
      case 137:
				return "NetBIOS Name Service";
      case 138:
				return "NetBIOS Datagram Service";
      case 139:
				return "NetBIOS Session Service";
      case 143:
				return "Internet Message Access Protocol (IMAP)";
      case 152:
				return "Background File Transfer Program (BFTP)";
      case 153:
				return "Simple Gateway Monitoring Protocol (SGMP)";
      case 156:
				return "SQL Service";
      case 162:
				return "Simple Network Management Protocol Trap (SNMPTRAP)";
      case 170:
				return "Print-srv, Network PostScript";
      case 175:
				return "VMNET (IBM z/VM, z/OS & z/VSE—Network Job Entry (NJE))";
      case 177:
				return "X Display Manager Control Protocol (XDMCP)";
      case 179:
				return "Border Gateway Protocol (BGP)";
      case 194:
				return "Internet Relay Chat (IRC)";
      case 199:
				return "SMUX, SNMP Unix Multiplexer";
      case 201:
				return "AppleTalk Routing Maintenance";
      case 209:
				return "Quick Mail Transfer Protocol";
      case 210:
				return "ANSI Z39.50";
      case 213:
				return "Internetwork Packet Exchange (IPX)";
      case 218:
				return "Message posting protocol (MPP)";
      case 220:
				return "Internet Message Access Protocol (IMAP), version 3";
      case 259:
				return "Efficient Short Remote Operations (ESRO)";
      case 262:
				return "Arcisdms";
      case 264:
				return "Border Gateway Multicast Protocol (BGMP)";
      case 280:
				return "http-mgmt";
      case 308:
				return "Novastor Online Backup";
      case 311:
				return "Mac OS X Server Admin";
      case 318:
				return "PKIX Time Stamp Protocol (TSP)";
      case 350:
				return "Mapping of Airline Traffic over Internet Protocol (MATIP) type A";
      case 351:
				return "Mapping of Airline Traffic over Internet Protocol (MATIP) type B";
      case 356:
				return "cloanto-net-1 (used by Cloanto Amiga Explorer and VMs)";
      case 366:
				return "On-Demand Mail Relay (ODMR)";
      case 369:
				return "Rpc2portmap";
      case 370:
				return "codaauth2, Coda authentication server";
      case 371:
				return "ClearCase albd";
      case 383:
				return "HP data alarm manager";
      case 384:
				return "A Remote Network Server System";
      case 387:
				return "AURP (AppleTalk Update-based Routing Protocol)[22]";
      case 389:
				return "Lightweight Directory Access Protocol (LDAP)";
      case 399:
				return "Digital Equipment Corporation DECnet (Phase V+) over TCP/IP";
      case 401:
				return "Uninterruptible power supply (UPS)";
      case 427:
				return "Service Location Protocol (SLP)";
      case 433:
				return "NNSP, part of Network News Transfer Protocol";
      case 434:
				return "Mobile IP Agent (RFC 5944)";
      case 443:
				return "Hypertext Transfer Protocol over TLS/SSL (HTTPS)";
      case 444:
				return "Simple Network Paging Protocol (SNPP), RFC 1568";
      case 445:
				return "Microsoft-DS Active Directory, Windows shares";
      case 464:
				return "Kerberos Change/Set password";
      case 465:
				return "URL Rendezvous Directory for SSM (Cisco protocol)";
      case 475:
				return "tcpnethaspsrv, Aladdin Knowledge Systems Hasp services";
      case 497:
				return "Dantz Retrospect";
      case 500:
				return "Internet Security Association and Key Management Protocol (ISAKMP) / Internet Key Exchange (IKE)";
      case 502:
				return "Modbus Protocol";
      case 504:
				return "Citadel, multiservice protocol for dedicated clients for the Citadel groupware system";
      case 510:
				return "FirstClass Protocol (FCP)";
      case 512:
				return "Rexec, Remote Process Execution";
      case 513:
				return "rlogin";
      case 514:
				return "Remote Shell, used to execute non-interactive commands on a remote system (Remote Shell, rsh, remsh)";
      case 515:
				return "Line Printer Daemon (LPD), print service";
      case 520:
				return "efs, extended file name server";
      case 524:
				return "NetWare Core Protocol (NCP)";
      case 530:
				return "Remote procedure call (RPC)";
      case 532:
				return "netnews";
      case 540:
				return "Unix-to-Unix Copy Protocol (UUCP)";
      case 542:
				return "commerce (Commerce Applications)";
      case 543:
				return "klogin, Kerberos login";
      case 544:
				return "kshell, Kerberos Remote shell";
      case 546:
				return "DHCPv6 client";
      case 547:
				return "DHCPv6 server";
      case 548:
				return "Apple Filing Protocol (AFP) over TCP";
      case 550:
				return "new-rwho, new-who";
      case 554:
				return "Real Time Streaming Protocol (RTSP)";
      case 556:
				return "Remotefs, RFS, rfs_server";
      case 563:
				return "NNTP over TLS/SSL (NNTPS)";
      case 587:
				return "e-mail message submission (SMTP)";
      case 591:
				return "FileMaker 6.0 (and later) Web Sharing (HTTP Alternate)";
      case 593:
				return "HTTP RPC Ep Map";
      case 601:
				return "Reliable Syslog Service";
      case 604:
				return "TUNNEL profile";
      case 631:
				return "Internet Printing Protocol (IPP)";
      case 635:
				return "RLZ DBase";
      case 636:
				return "Lightweight Directory Access Protocol over TLS/SSL (LDAPS)";
      case 639:
				return "MSDP, Multicast Source Discovery Protocol";
      case 641:
				return "SupportSoft Nexus Remote Command (control/listening)";
      case 643:
				return "SANity";
      case 646:
				return "Label Distribution Protocol (LDP)";
      case 647:
				return "DHCP Failover protocol";
      case 648:
				return "Registry Registrar Protocol (RRP)";
      case 651:
				return "IEEE-MMS";
      case 653:
				return "SupportSoft Nexus Remote Command";
      case 654:
				return "Media Management System (MMS) Media Management Protocol (MMP)";
      case 657:
				return "IBM RMC (Remote monitoring and Control) protocol";
      case 660:
				return "Mac OS X Server administration";
      case 674:
				return "Application Configuration Access Protocol (ACAP)";
      case 688:
				return "REALM-RUSD (ApplianceWare Server Appliance Management Protocol)";
      case 690:
				return "Velneo Application Transfer Protocol (VATP)";
      case 691:
				return "MS Exchange Routing";
      case 694:
				return "Linux-HA high-availability heartbeat";
      case 695:
				return "IEEE Media Management System over SSL (IEEE-MMS-SSL)";
      case 700:
				return "Extensible Provisioning Protocol (EPP)";
      case 701:
				return "Link Management Protocol (LMP)";
      case 702:
				return "IRIS (Internet Registry Information Service) over BEEP";
      case 706:
				return "Secure Internet Live Conferencing (SILC)";
      case 711:
				return "Cisco Tag Distribution Protocol";
      case 712:
				return "Topology Broadcast based on Reverse-Path Forwarding routing protocol (TBRPF)";
      case 749:
				return "Kerberos (protocol) administration";
      case 753:
				return "Reverse Routing Header (RRH)";
      case 754:
				return "tell send";
      case 800:
				return "mdbs-daemon";
      case 830:
				return "NETCONF over SSH";
      case 831:
				return "NETCONF over BEEP";
      case 832:
				return "NETCONF for SOAP over HTTPS";
      case 833:
				return "NETCONF for SOAP over BEEP";
      case 847:
				return "DHCP Failover protocol";
      case 848:
				return "Group Domain Of Interpretation (GDOI) protocol";
      case 860:
				return "iSCSI (RFC 3720)";
      case 861:
				return "OWAMP control (RFC 4656)";
      case 862:
				return "TWAMP control (RFC 5357)";
      case 873:
				return "rsync file synchronization protocol";
      case 902:
				return "ideafarm-door (IdeaFarm (tm) Operations)";
      case 903:
				return "ideafarm-panic (IdeaFarm (tm) Operations)";
      case 989:
				return "FTPS Protocol (data), FTP over TLS/SSL";
      case 990:
				return "FTPS Protocol (control), FTP over TLS/SSL";
      case 991:
				return "Netnews Administration System (NAS)";
      case 992:
				return "Telnet protocol over TLS/SSL";
      case 993:
				return "Internet Message Access Protocol over TLS/SSL (IMAPS)";
      case 994:
				return "Internet Relay Chat over TLS/SSL (IRCS)";
      case 995:
				return "Post Office Protocol 3 over TLS/SSL (POP3S)";
      case 1025:
				return "Microsoft RPC";
      case 1080:
				return "SOCKS Proxy";
      case 1194:
				return "OpenVPN";
      case 1241:
				return "Nessus";
      case 1311:
				return "Dell OpenManage";
      case 1433:
				return "Microsoft SQL";
      case 1434:
				return "Microsoft SQL";
      case 1512:
				return "WINS";
      case 1589:
				return "Cisco VQP";
      case 1701:
				return "L2TP";
      case 1723:
				return "MS PPTP";
      case 1741:
				return "CiscoWorks 2000";
      case 1812:
				return "RADIUS";
      case 1813:
				return "RADIUS";
      case 1985:
				return "Cisco HSRP";
      case 2000:
				return "Cisco SCCP";
      case 2002:
				return "Cisco ACS";
      case 2049:
				return "NFS";
      case 2082:
				return "cPanel";
      case 2083:
				return "cPanel";
      case 2100:
				return "Oracle XDB";
      case 2222:
				return "DirectAdmin";
      case 2483:
				return "Oracle DB";
      case 2484:
				return "Oracle DB";
      case 3050:
				return "Interbase DB";
      case 3124:
				return "HTTP Proxy";
      case 3128:
				return "HTTP Proxy";
      case 3222:
				return "GLBP";
      case 3260:
				return "iSCSI Target";
      case 3306:
				return "MySQL";
      case 3389:
				return "Terminal Server";
      case 3689:
				return "iTunes";
      case 3690:
				return "Subversion";
      case 4333:
				return "mSQL";
      case 4664:
				return "Google Desktop";
      case 4899:
				return "Radmin";
      case 5000:
				return "UPnP";
      case 5001:
				return "iperf";
      case 5004:
				return "RTP";
      case 5005:
				return "RTP";
      case 5432:
				return "PostgreSQL";
      case 5500:
				return "VNC Server";
      case 5631:
				return "pcAnywhere";
      case 5632:
				return "pcAnywhere";
      case 5800:
				return "VNC over HTTP";
      case 6000:
				return "X11";
      case 6001:
				return "X11";
      case 6129:
				return "DameWare";
      case 6566:
				return "SANE";
      case 6588:
				return "AnalogX";
      case 8080:
				return "HTTP Proxy";
      case 8200:
				return "VMware Server";
      case 8500:
				return "Adobe ColdFusion";
      case 9100:
				return "HP JetDirect";
      case 9101:
				return "Bacula";
      case 9102:
				return "Bacula";
      case 9103:
				return "Bacula";
      case 9800:
				return "WebDAV";
      case 10000:
				return "Webmin";
      case 11371:
				return "OpenPGP";
      case 13720:
				return "NetBackup";
      case 13721:
				return "NetBackup";
      case 19226:
				return "AdminSecure";
      case 19638:
				return "Ensim";
      case 20000:
				return "Usermin";
      case 24800:
				return "Synergy";
    }

    return "No port description";
  }

  private final InetAddress inetAddress;
}
