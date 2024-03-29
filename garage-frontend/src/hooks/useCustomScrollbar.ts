const useCustomScrollbar = () => {
  return {
    "&::-webkit-scrollbar": {
      width: "16px",
      borderRadius: "8px",
      backgroundColor: `rgba(0, 0, 0, 0.05)`,
    },
    "&::-webkit-scrollbar-thumb": {
      backgroundColor: `rgba(0, 0, 0, .5)`,
      borderRadius: "8px",
    },
  };
};

export default useCustomScrollbar;
