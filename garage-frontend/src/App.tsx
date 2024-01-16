import { Grid, GridItem } from "@chakra-ui/react";
import NavigationBar from "./components/navigation/NavigationBar";
import { Route, Routes } from "react-router-dom";
import CarPanel from "./components/car/CarPanel";
import ServicePanel from "./components/service/ServicePanel";
import EmployeePanel from "./components/employee/EmployeePanel";
import ClientPanel from "./components/client/ClientPanel";
import CarPartPanel from "./components/car-part/CarPartPanel";
import DashboardPanel from "./components/dashboard/DashboardPanel";
function App() {
  return (
    <Grid
      templateAreas={{ lg: `"navbar" "mainpanel"` }}
      templateRows="repeat(1, 1fr)"
      templateColumns="repeat(5, 1fr)"
      gap={4}
      height={"100%"}
    >
      <GridItem colSpan={1}>
        <NavigationBar />
      </GridItem>

      <GridItem colSpan={4}>
        <Routes>
          <Route path="/" element={<DashboardPanel />}></Route>
          <Route path="cars" element={<CarPanel />}></Route>

          <Route path="services" element={<ServicePanel />}></Route>

          <Route path="employees" element={<EmployeePanel />}></Route>

          <Route path="clients" element={<ClientPanel />}></Route>
          <Route path="parts" element={<CarPartPanel />} />
        </Routes>
      </GridItem>
    </Grid>
  );
}

export default App;
