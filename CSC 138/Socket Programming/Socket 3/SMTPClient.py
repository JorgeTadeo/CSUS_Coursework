from socket import*

msg = 'I love networking'
endmsg = '\r\n.\r\n'

#Connection SETUP
target_server = ('smtp.csus.edu',25)
clientSocket = socket(AF_INET,SOCK_STREAM)
clientSocket.connect(target_server)
isConnected = clientSocket.recv(1024)
print isConnected
if isConnected[:3] != '220':
	print '220 reply not received from server.'

#SEND HELO
clientSocket.send('HELO Alice\r\n')
recvhelo = clientSocket.recv(1024)
print (recvhelo.rstrip() + ' , please to meet you.\n')
if recvhelo[:3] != '250':
	print '250 reply not received from server.'
	

#SENDER SETUP 
clientSocket.send("MAIL From: brain@csus.edu\r\n")
recv2 = clientSocket.recv(1024)
print recv2
if recv2[:3] != '250':
	print '250 reply not received from server.'
	
#RECEIVER SETUP
clientSocket.send("RCPT TO: isolvethis@gmail.com\r\n")
recv2 = clientSocket.recv(1024)
print recv2
if recv2[:3] != '250':
	print '250 reply not received from server.'


	
#Send DATA command and print server response.
clientSocket.send("DATA\r\n")
recv2 = clientSocket.recv(1024)
print recv2
if recv2[:3] != '354':
	print '354 reply not received from server.'

#Send Data and print server response.
clientSocket.send("SUBJECT: SMTP Mail Client Test\r\n\n")
clientSocket.send('This is a msg...\n')
clientSocket.send(endmsg)
#clientSocket.send(endmsg)
recv2 = clientSocket.recv(1024)
print recv2
if recv2[:3] != '250':
	print '250 reply not received from server.'

	


	
#Send QUIT and print server response.
clientSocket.send("QUIT\r\n")
recv2 = clientSocket.recv(1024)
print recv2
if recv2[:3] != '221':
	print '250 reply not received from server.'

print "Mail Sent"
	
	
	
clientSocket.close()


