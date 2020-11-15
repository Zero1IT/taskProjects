import {Container} from "@material-ui/core";
import React from "react";
import {useHistory} from "react-router-dom";
import List from "@material-ui/core/List";
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MailIcon from '@material-ui/icons/Mail';
import MenuIcon from '@material-ui/icons/Menu';

function SignIn() {

  const history = useHistory();

  function clickHandler(url) {
    return () => {
      history.push(url);
    }
  }

  return (
    <Container>
      <div>
        <List>
          {
            ["Authenticate", "Registration"].map((text, index) => (
              <ListItem button key={text} onClick={clickHandler(index % 2 === 0 ? "/authenticate" : "/registration")}>
                <ListItemIcon>{index % 2 === 0 ? <MenuIcon /> : <MailIcon />}</ListItemIcon>
                <ListItemText primary={text} />
              </ListItem>
            ))
          }
        </List>
      </div>
    </Container>
  )
}

export {
    SignIn
}