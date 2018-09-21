import React from "react"
import ReactDOM from "react-dom"
import "./index.css"
import makeApp from "./makeApp"
import registerServiceWorker from "./registerServiceWorker"

const App = makeApp()
ReactDOM.render(<App />, document.getElementById("root"))
registerServiceWorker()
