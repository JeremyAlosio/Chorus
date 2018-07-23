@TestFunctionality
Feature: Single Test Stress Test

  Scenario Outline: Test
    Given the user logs in as <USERNAME> with the password <PASSWORD>

    @TestEnv
    Examples: 
      | USERNAME         | PASSWORD |
      | BERRY_HRA_FSA_00 | Test2day |

    @Stage @OfflinePrd @Production
    Examples: 
      | USERNAME    | PASSWORD |
      | JPMC_HSID_2 | Test2day |
