import React from "react"
import { Route } from "react-router-dom"

import HomePage from "./pages/HomePage"
import BlogHomePage from "./pages/BlogHomePage"
import BlogEntryPage from "./pages/BlogEntryPage"
import BuilderPage from "./pages/BuilderPage"
import GamePage from "./pages/GamePage"
import LoginPage from "./pages/LoginPage"

import ScrollToTop from "./ScrollToTop"
import makeDucksRoot from "./ducks/makeDucksRoot"

// Flexbox Styles:
// https://philipwalton.github.io/solved-by-flexbox/
// https://css-tricks.com/snippets/css/a-guide-to-flexbox/

const makeApp = (initialState, ...middleware) => {
  const DucksRoot = makeDucksRoot(initialState, ...middleware)

  const App = () => (
    <DucksRoot>
      <ScrollToTop>
        <Route exact path="/" component={HomePage} />
        <Route exact path="/blog" component={BlogHomePage} />
        <Route path="/blog/:entryId" component={BlogEntryPage} />
        <Route exact path="/builder" component={BuilderPage} />
        <Route exact path="/game" component={GamePage} />
        <Route exact path="/login" component={LoginPage} />
      </ScrollToTop>
    </DucksRoot>
  )

  return App
}

export default makeApp
