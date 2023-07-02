# Support Aggregation Hub Documentation

## Introduction

The Support Aggregation Hub is a backend service designed to consolidate customer complaints based on common denominators. Its primary goal is to improve developer productivity and enhance customer response time by ensuring efficient handling of CRM cases. This documentation provides an overview of the Support Aggregation Hub, its features, and instructions on how to integrate and use the service effectively.

## Business Requirements

The following are the key business requirements for the Support Aggregation Hub:

1. **Integrate with CRM systems**: The Support Aggregation Hub integrates with two different CRM systems called "Banana" and "Strawberry." The service retrieves CRM case data from these systems to consolidate and manage customer complaints effectively.

2. **Cases Data freshness**: The Support Aggregation Hub must fetch all CRM cases periodically to ensure fresh data availability. The default interval for data retrieval is every 4 hours. However, this interval can be adjusted based on future requirements.

3. **On-demand aggregation**: The service provides an option to manually trigger data refresh when the fetched information from CRM tools is more than 15 minutes old. This ensures that up-to-date and accurate information is available to the users upon request.

4. **Filtering**: The Support Aggregation Hub supports filtering of CRM cases based on specific parameters. The service allows searching and filtering based on the following criteria:
    - Provider Name (equivalent to "provider" in the response JSON)
    - Error code
    - Case status (open/closed)

## Integration with CRM Systems

The Support Aggregation Hub integrates with two CRM systems, "Banana" and "Strawberry." The service retrieves case data from these systems using the following endpoints:

1. **Banana CRM**:
    - Endpoint: `GET http://localhost:8080/banana`
    - Output format:
       ```json
       {
          "data": [
             {
                "Case ID": 1,
                "Customer_ID": 818591,
                "Provider": 6111,
                "CREATED_ERROR_CODE": 324,
                "STATUS": "Open",
                "TICKET_CREATION_DATE": "3/14/2019 16:30",
                "LAST_MODIFIED_DATE": "3/17/2019 3:41",
                "PRODUCT_NAME": "BLUE"
             },
             ...
          ]
       }
       ```

2. **Strawberry CRM**:
    - Endpoint: `GET http://localhost:8080/strawberry`
    - Output format:
       ```json
       {
          "data": [
             {
                "Case ID": 1,
                "Customer ID": 818591,
                "Provider": 10001121,
                "CREATED_ERROR_CODE": 101,
                "STATUS": "Closed",
                "TICKET_CREATION_DATE": "4/1/2019 17:25",
                "LAST_MODIFIED_DATE": "4/2/2019 8:00",
                "PRODUCT_NAME": "RED"
             },
             ...
          ]
       }
       ```

Please note that the provided URLs are placeholders and do not actually exist. You should replace them with the appropriate URLs to integrate with the actual CRM systems.

## Functionality

The Support Aggregation Hub provides the following functionality to manage CRM cases effectively:

1. **Consolidation**: The service groups CRM cases together based on common denominators. This ensures that engineers do not work on separate cases that are essentially the same issue, resulting in improved response time for customers.

2. **Data Refresh**: The Support Aggregation Hub fetches CRM case data periodically to maintain fresh and up-to-date information. By default, the data is fetched

every X hours, with the initial value set to 4. However, this interval can be adjusted based on future requirements.

3. **On-demand Data Refresh**: The service supports an on-demand data refresh feature. If the fetched data from CRM tools is more than 15 minutes old, users can trigger a live data refresh by clicking the refresh button. This ensures that users have access to the most recent information.

4. **Filtering**: The Support Aggregation Hub allows users to filter CRM cases based on specific parameters. Users can search and filter cases by Provider Name, Error code, and Case status. By default, all results are returned without any filtering.

## Usage

To effectively use the Support Aggregation Hub, follow these guidelines:

1. **Integration**: Integrate the Support Aggregation Hub backend service with your existing systems and CRM tools. Configure the appropriate CRM endpoints for "Banana" and "Strawberry" systems in the service configuration.

2. **Data Fetch Interval**: By default, the service fetches CRM case data every 4 hours. If you need to adjust the data fetch interval, modify the corresponding configuration parameter.

3. **On-demand Data Refresh**: To trigger a live data refresh, click the refresh button available in the user interface. This ensures that the displayed information is up-to-date.

4. **Filtering**: To filter CRM cases, use the provided search parameters. Specify one or more of the following filters:
    - Provider: Enter the name of the provider to filter cases associated with that provider.
    - Error code: Enter the error code to filter cases having that specific error code.
    - Case status: Specify "open" or "closed" to filter cases based on their status.

## Conclusion

The Support Aggregation Hub is a powerful backend service designed to streamline the management of CRM cases. By consolidating customer complaints based on common denominators, the service enhances developer productivity and improves response time for customers. Integration with CRM systems, regular data fetches, on-demand data refresh, and filtering capabilities ensure efficient handling of customer complaints.