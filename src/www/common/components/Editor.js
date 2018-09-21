import React, { Component } from "react"
import inputClassName from "../styles/inputClassName"

class Editor extends Component {
  onChange = ev => this.props.onChange(ev.target.value)

  render() {
    return (
      <textarea
        rows={Math.min(10, (this.props.value || "").split("\n").length)}
        {...this.props}
        className={`${inputClassName} ${this.props.className}`}
        onChange={this.onChange}
      />
    )
  }
}

export default Editor
