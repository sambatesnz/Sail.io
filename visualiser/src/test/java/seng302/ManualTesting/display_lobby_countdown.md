#Manual Testing Document

##### Test Race Countdown Timer Displaying
#####Context:
    When only one player is in the lobby for a race, no countdown time should be displayed.
    Run the server.
    Connect a client.
    Connect another client.
    Disconnect one of the clients (before the 1 minute mark).
    Then reconnect another client.

#####Expected Results:
    No countdown time should display when only one user is in the lobby.
    When the second client connects the countdown should display and start counting down.
    Disconnecting one of the clients before the one minute mark stops displaying the countdown again.
    When the next client connects and there is 2 clients in the lobby the race clock reappears with the full race countdown remaining.


####Testing log:

#####Test:

- Date: 06/09/2017
- Current Commit of branch: 5433e5fb7de424e9236fcb317d674c7810ddc34b
- Performed By: Tim
- Result: *Fail* (timer reset but did not stop displaying when 2nd client disconnected)

#####Test:

- Date: 06/09/2017
- Current Commit of branch: fefa9f4
- Performed By: Tim
- Result: *Pass*

#####Test:

- Date: 06/09/2017
- Current Commit of branch: ec2648ec294cb80d899c793ce1692e45135d0d30
- Performed By: Jono
- Result: *Pass*