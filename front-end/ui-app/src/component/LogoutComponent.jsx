import React, {Component} from 'react'
import Button from "@mui/material/Button";

class LogoutComponent extends Component {
  render() {
    return (
      <>
        <h1>You are logged out</h1>
        <div className="container">
          Thank You for Using Our Application.
        </div>
        <Button variant="contained" href={"/"}>Sign in</Button>
      </>
    )
  }
}

export default LogoutComponent