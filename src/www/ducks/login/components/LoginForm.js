import React, { Component } from "react"

import Input from "../../../common/components/Input"
import Button from "../../../common/components/Button"

class LoginForm extends Component {
  onSubmit = () => this.props.onSubmit(this.props.username)

  render() {
    const { username, onUsername } = this.props

    return (
      <div>
        Username: <br />
        <Input value={username} onChange={onUsername} />
        <br />
        <br />
        <Button onClick={this.onSubmit}>[ Login ]</Button>
      </div>
    )
  }
}

export default LoginForm
