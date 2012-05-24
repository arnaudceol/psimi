/**
 * Copyright 2011 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hupo.psi.calimocho.key;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface InteractionKeys {

    String KEY_ID_A = "idA";
    String KEY_ID_B = "idB";
    String KEY_ALTID_A = "altidA";
    String KEY_ALTID_B = "altidB";
    String KEY_ALIAS_A = "aliasA";
    String KEY_ALIAS_B = "aliasB";
    String KEY_DETMETHOD = "detmethod_exact";
    String KEY_PUBAUTH = "pubauth";
    String KEY_PUBID = "pubid";
    String KEY_TAXID_A = "taxidA";
    String KEY_TAXID_B = "taxidB";
    String KEY_INTERACTION_TYPE = "type_exact";
    String KEY_SOURCE = "source";
    String KEY_INTERACTION_ID = "interaction_id";
    String KEY_CONFIDENCE = "confidence";

    String KEY_DATASET = "dataset";

    String KEY_EXPANSION = "expansion";
    String KEY_BIOROLE_A = "bioRoleA";
    String KEY_BIOROLE_B = "bioRoleB";
    String KEY_EXPROLE_A = "expRoleA";
    String KEY_EXPROLE_B = "expRoleB";
    String KEY_INTERACTOR_TYPE_A = "typeA";
    String KEY_INTERACTOR_TYPE_B = "typeB";
    String KEY_XREFS_A = "xrefsA";
    String KEY_XREFS_B = "xrefsB";
    String KEY_XREFS_I = "xrefsI";
    String KEY_ANNOTATIONS_A = "annotationsA";
    String KEY_ANNOTATIONS_B = "annotationsB";
    String KEY_ANNOTATIONS_I = "annotationsI";
    String KEY_HOST_ORGANISM = "hostOrganism";
    String KEY_PARAMETERS_A = "parametersA";
    String KEY_PARAMETERS_B = "parametersB";
    String KEY_PARAMETERS_I = "parametersI";
    String KEY_CREATION_DATE = "creationDate";
    String KEY_UPDATE_DATE = "updateDate";
    String KEY_CHECKSUM_A = "checksumA";
    String KEY_CHECKSUM_B = "checksumB";
    String KEY_CHECKSUM_I = "checksumI";
    String KEY_NEGATIVE = "negative";
    String KEY_FEATURE_A = "ftypeA";
    String KEY_FEATURE_B = "ftypeB";
    String KEY_STOICHIOMETRY_A = "stcA";
    String KEY_STOICHIOMETRY_B = "stcB";
    String KEY_PART_IDENT_METHOD_A = "pmethodA";
    String KEY_PART_IDENT_METHOD_B = "pmethodB";
}
