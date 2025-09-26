#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Apr 15 12:27:14 2025

@author: reidmaury
"""

import matplotlib.pyplot as plt
plt.style.use('classic')
import seaborn as sns
sns.set()
import numpy as np
import pandas as pd

scd = pd.read_csv('Final Project/sugar_consumption_dataset.csv')
scd.head()

scd.info()
scd.describe()

scdDF = pd.DataFrame({'Country': scd['Country'], 
                      'Year': scd['Year'], 
                      'Population': scd['Population'], 
                      'Total_Sugar_Consumption': scd['Total_Sugar_Consumption'], 
                      'Sugar_From_Sugarcane': scd['Sugar_From_Sugarcane'],
                      'Sugar_From_Beet': scd['Sugar_From_Beet'],
                      'Sugar_From_HFCS': scd['Sugar_From_HFCS'],
                      'Sugar_From_Other': scd['Sugar_From_Other'],
                      'Avg_Daily_Sugar_Intake': scd['Avg_Daily_Sugar_Intake'],
                      'Diabetes_Prevalence': scd['Diabetes_Prevalence'],
                      'Obesity_Rate': scd['Obesity_Rate']})
scdDF


yearAbove2000 = scdDF[scdDF['Year'] >= 2000]
yearAbove2000 
yearBelow2000 = scdDF[(scdDF['Year'] <= 1999) & (scdDF['Year'] >= 1976)]
yearBelow2000


#avg sugar sources above and below 2000
avg_sugar_sources_above2000 = yearAbove2000.groupby('Country')[['Sugar_From_Sugarcane', 
                                                                 'Sugar_From_Beet', 
                                                                 'Sugar_From_HFCS', 
                                                                 'Sugar_From_Other']].mean()
avg_sugar_sources_above2000

avg_sugar_sources_above2000.plot(kind='bar', stacked=True, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Sugar Source Composition by Country (2000 and after)')
plt.ylabel('Average Sugar Consumption')
plt.xlabel('Country')
plt.legend(title='Sugar Source', bbox_to_anchor=(1.05, 1), loc='upper left')
plt.show()


avg_sugar_sources_below2000 = yearBelow2000.groupby('Country')[['Sugar_From_Sugarcane', 
                                                                 'Sugar_From_Beet', 
                                                                 'Sugar_From_HFCS', 
                                                                 'Sugar_From_Other']].mean()
avg_sugar_sources_below2000

avg_sugar_sources_below2000.plot(kind='bar', stacked=True, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Sugar Source Composition by Country (1976 to 1999)')
plt.ylabel('Average Sugar Consumption')
plt.xlabel('Country')
plt.legend(title='Sugar Source', bbox_to_anchor=(1.05, 1), loc='upper left')
plt.show()



#avg sugarcane above and below 2000
avg_sugarcane_above2000 = yearAbove2000.groupby('Country')[['Sugar_From_Sugarcane']].mean()
avg_sugarcane_above2000

avg_sugarcane_above2000.plot(kind='bar', stacked=False, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Sugarcane Composition by Country (2000 and after)')
plt.ylabel('Average Sugarcane Consumption')
plt.xlabel('Country')
plt.show()


avg_sugarcane_below2000 = yearBelow2000.groupby('Country')[['Sugar_From_Sugarcane']].mean()
avg_sugarcane_below2000

avg_sugarcane_below2000.plot(kind='bar', stacked=False, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Sugarcane Composition by Country (1976 to 1999)')
plt.ylabel('Average Sugarcane Consumption')
plt.xlabel('Country')
plt.show()



#avg obesity above and below 2000
avg_obesity_above2000 = yearAbove2000.groupby('Country')[['Obesity_Rate']].mean()
avg_obesity_above2000

avg_obesity_above2000.plot(kind='bar', stacked=False, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Obesity by Country (2000 and after)')
plt.ylabel('Average Obesity')
plt.xlabel('Country')
plt.show()


avg_obesity_below2000 = yearBelow2000.groupby('Country')[['Obesity_Rate']].mean()
avg_obesity_below2000

avg_obesity_below2000.plot(kind='bar', stacked=False, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Obesity by Country (1976 to 1999)')
plt.ylabel('Average Obesity')
plt.xlabel('Country')
plt.show()



#avg diabetes above and below 2000
avg_diabetes_above2000 = yearAbove2000.groupby('Country')[['Diabetes_Prevalence']].mean()
avg_diabetes_above2000

avg_diabetes_above2000.plot(kind='bar', stacked=False, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Diabetes by Country (2000 and after)')
plt.ylabel('Average Diabetes')
plt.xlabel('Country')
plt.show()


avg_diabetes_below2000 = yearBelow2000.groupby('Country')[['Diabetes_Prevalence']].mean()
avg_diabetes_below2000

avg_diabetes_below2000.plot(kind='bar', stacked=False, figsize=(12, 6),
                                      colormap='tab20c', edgecolor='black')
plt.title('Average Diabetes by Country (1976 to 1999)')
plt.ylabel('Average Diabetes')
plt.xlabel('Country')
plt.show()



#scatter plots for mean of avg daily sugar intake, obesity rate, and diabetes prevelance above and below 2000 
means_above2000 = yearAbove2000.groupby('Country')[['Avg_Daily_Sugar_Intake', 'Obesity_Rate', 'Diabetes_Prevalence']].mean().reset_index()
means_above2000

plt.figure(figsize=(10, 6))
sns.scatterplot(data=means_above2000, x='Avg_Daily_Sugar_Intake', y='Obesity_Rate', hue='Country', s=100, palette='tab10')
plt.title('Average Sugar Intake vs. Obesity Rate (2000 and after)')
plt.xlabel('Avg Daily Sugar Intake (g/person/day)')
plt.ylabel('Obesity Rate')
plt.grid(True)
plt.show()

plt.figure(figsize=(10, 6))
sns.scatterplot(data=means_above2000, x='Avg_Daily_Sugar_Intake', y='Diabetes_Prevalence', hue='Country', s=100, palette='tab10')
plt.title('Average Sugar Intake vs. Diabetes Prevalence (2000 and after)')
plt.xlabel('Avg Daily Sugar Intake (g/person/day)')
plt.ylabel('Diabetes Prevalence')
plt.grid(True)
plt.show()

means_below2000 = yearBelow2000.groupby('Country')[['Avg_Daily_Sugar_Intake', 'Obesity_Rate', 'Diabetes_Prevalence']].mean().reset_index()
means_below2000

plt.figure(figsize=(10, 6))
sns.scatterplot(data=means_below2000, x='Avg_Daily_Sugar_Intake', y='Obesity_Rate', hue='Country', s=100, palette='tab10')
plt.title('Average Sugar Intake vs. Obesity Rate (1976 to 1999)')
plt.xlabel('Avg Daily Sugar Intake (g/person/day)')
plt.ylabel('Obesity Rate')
plt.grid(True)
plt.show()

plt.figure(figsize=(10, 6))
sns.scatterplot(data=means_below2000, x='Avg_Daily_Sugar_Intake', y='Diabetes_Prevalence', hue='Country', s=100, palette='tab10')
plt.title('Average Sugar Intake vs. Diabetes Prevalence (1976 to 1999)')
plt.xlabel('Avg Daily Sugar Intake (g/person/day)')
plt.ylabel('Diabetes Prevalence')
plt.grid(True)
plt.show()



#Trends over time for country yearly means of avg daily sugar intake, obesity rate, and diabetes prevelance
country_yearly_means = scdDF.groupby(['Country','Year'])[['Avg_Daily_Sugar_Intake', 'Obesity_Rate', 'Diabetes_Prevalence']].mean().reset_index()

countries = scdDF['Country'].unique()

for country in countries:
    data = country_yearly_means[country_yearly_means['Country'] == country]
    plt.figure(figsize=(10, 5))
    plt.plot(data['Year'], data['Avg_Daily_Sugar_Intake'], label='Sugar Intake')
    plt.plot(data['Year'], data['Obesity_Rate'], label='Obesity Rate')
    plt.plot(data['Year'], data['Diabetes_Prevalence'], label='Diabetes Prevalence')
    plt.title(f'{country}: Health Trends Over Time')
    plt.xlabel('Year')
    plt.ylabel('Average (g/day or %)')
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.show()




# SD above 2000
std_above2000 = yearAbove2000.groupby('Country')[['Avg_Daily_Sugar_Intake', 'Obesity_Rate', 'Diabetes_Prevalence']].std()
print("Standard Deviation (2000 and after):\n", std_above2000)

# SD below 2000
std_below2000 = yearBelow2000.groupby('Country')[['Avg_Daily_Sugar_Intake', 'Obesity_Rate', 'Diabetes_Prevalence']].std()
print("Standard Deviation (1976 to 1999):\n", std_below2000)




# Corr for years above 2000
corr_above2000 = means_above2000[['Avg_Daily_Sugar_Intake', 'Obesity_Rate', 'Diabetes_Prevalence']].corr()
print("Correlation matrix (2000 and after):\n", corr_above2000)

# Corr for years below 2000
corr_below2000 = means_below2000[['Avg_Daily_Sugar_Intake', 'Obesity_Rate', 'Diabetes_Prevalence']].corr()
print("Correlation matrix (1976–1999):\n", corr_below2000)




