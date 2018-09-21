import React from "react"
import { Redirect } from "react-router"

import StateGameView from "../ducks/game/components/StateGameView"
import BasicLayout from "../layouts/BasicLayout"
import IfLoginRequired from "../ducks/login/components/IfLoginRequired"

const GamePage = () => (
  <BasicLayout breadCrumb="> Game">
    <IfLoginRequired>
      <Redirect to="/login" />
    </IfLoginRequired>

    <br />
    <StateGameView />
  </BasicLayout>
)

export default GamePage
