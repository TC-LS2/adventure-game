import React from "react"
import ReactDOM from "react-dom"
import makeApp from "./makeApp"
const App = makeApp()

it("renders without crashing", () => {
  const div = document.createElement("div")
  ReactDOM.render(<App />, div)
  ReactDOM.unmountComponentAtNode(div)
})
