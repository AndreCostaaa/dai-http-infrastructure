import { Grid, GridItem } from "@chakra-ui/react";
import NavBar from "./components/navigation/NavBar";
import MainPanel from "./components/MainPanel";

function App() {
  return (
    <Grid
      templateAreas={{ lg: `"navbar" "mainpanel"` }}
      h="95vh"
      templateRows="repeat(1, 1fr)"
      templateColumns="repeat(5, 1fr)"
      gap={4}
    >
      <GridItem
        colSpan={1}
        borderColor={"#7f7f7f"}
        borderWidth={1}
        borderRadius={20}
      >
        <NavBar />
      </GridItem>

      <GridItem
        colSpan={4}
        borderColor={"#7f7f7f"}
        borderWidth={1}
        borderRadius={20}
      />
    </Grid>
  );
}

export default App;
