## pantry_car

#### Restaurant table
- name
- type
- station_id
- distance
- contact_person
- restaurant_number(what does this mean?)
- open_time
- close_time
- sla_details? (this can be used to see if a restaurant can provide service based on the order time)
- minimum_order_value
- delivery_charges
- is_online
- bank_details_id
- tax_details_id
- location_id


#### TaxDetails(all information here needs to be encrypted)
- service_tax_number
- vat_no
- food_license
- FSSAI
- mou_acceptance

#### BankDetails(all information here needs to be encrypted)
- bank account_details
- bank_Account_name
- a/c email
- a/c no
- bank name
- band_location_id
- ifsc_code

#### Contact
- id
- email_id
- name
- restaurant_id
- designation_id

#### Designations
- id
- designation_name


#### Location
- address
- city
- state
- pincode

#### Station
- id
- station_full_name
- state( not sure if needed to add more clarity?)
- station_short_name
- station_code

#### CloseDates
- type
- value

#### CloseDates_restaurant_mapping
- close_day_id
- restaurant_id


