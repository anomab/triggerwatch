# TriggerWatch

## Current Functionality (v1)
- Frontend accepts fullName + textBody
- Backend checks for trigger words using contains()
- Collects ALL trigger words
- Tokenized name and text in a HashSet to ensure uniqueness and fast look-up
- No database yet
- Email sent when triggerwords are found
- Added data pre-processing
