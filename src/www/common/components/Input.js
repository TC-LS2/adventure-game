import React, { Component } from "react"
import inputClassName from "../styles/inputClassName"

class Input extends Component {
  onChange = ev => this.props.onChange(ev.target.value)

  render() {
    return (
      <input
        {...this.props}
        className={`${inputClassName} ${this.props.className}`}
        onChange={this.onChange}
      />
    )
  }
}

export default Input
