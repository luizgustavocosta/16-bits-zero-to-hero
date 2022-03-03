import React, {Component} from 'react'
import {withRouter} from 'react-router-dom'
import AuthenticationService from '../service/AuthenticationService';
import Button from "@mui/material/Button";

class MenuComponent extends Component {

  render() {
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

    return (
      <header>
        {isUserLoggedIn &&
        <Button href={"/logout"}
                onClick={AuthenticationService.logout}
                variant="contained"
                align="right"
                color={"secondary"}
                sx={{ mt: 3, mb: 2 }}>Logout</Button>
        }
      </header>
    )
  }
}

export default withRouter(MenuComponent)