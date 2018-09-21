import React from "react"

import LinkButton from "../common/components/LinkButton"
import BasicLayout from "../layouts/BasicLayout"

const Home = () => (
  <BasicLayout>
    <h1>Welcome!</h1>
    <LinkButton to="/game">[ PLAY ▶ ]</LinkButton>
  </BasicLayout>
)

export default Home
