import { Component } from "react"
import { withRouter } from "react-router"

let prevHistoryLength = window.history.length

class ScrollToTop extends Component {
  componentDidUpdate(prevProps) {
    const currentHistoryLength = window.history.length

    if (currentHistoryLength > prevHistoryLength)
      if (this.props.location !== prevProps.location) window.scrollTo(0, 0)

    prevHistoryLength = currentHistoryLength
  }

  render() {
    return this.props.children
  }
}

export default withRouter(ScrollToTop)
