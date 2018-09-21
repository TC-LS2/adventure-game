import React, { Component } from "react"

import Button from "./Button"

class Toggle extends Component {
  state = { visible: !!this.props.firstVisible }

  onToggle = () => this.setState(({ visible }) => ({ visible: !visible }))

  render() {
    const { title, titleOn, titleOff, children } = this.props
    const { visible } = this.state

    return (
      <React.Fragment>
        <Button onClick={this.onToggle}>
          {(visible ? titleOn : titleOff) || title}
        </Button>
        {visible && children}
      </React.Fragment>
    )
  }
}

export default Toggle
